package com.zhanyf.hadoop.zk.controller;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhanyf.hadoop.zk.domain.Cluster;
import com.zhanyf.hadoop.zk.domain.HostPerformance;
import com.zhanyf.hadoop.zk.exception.ServiceException;
import com.zhanyf.hadoop.zk.monitor.GlobalInstance;
import com.zhanyf.hadoop.zk.service.ClusterService;

@Controller
@RequestMapping(value = "/host")
public class HostPerformanceController {

	@Autowired
	private ClusterService clusterService;

	@RequestMapping("/index")
	public ModelAndView mainPage(String clusterId, HttpServletResponse response, Map<String, Object> model)
			throws ServiceException {
		Map<Integer, Cluster> clusterMap = GlobalInstance.getAllClusters();
		// if(StringUtils.isBlank(clusterId)) {
		// int minId = 1;
		// for (Integer id : clusterMap.keySet()) {
		// if(minId> id)
		// minId = id;
		// }
		// clusterId = String.valueOf(minId);
		// }

		clusterId = StringUtils.defaultIfBlank(clusterId, "1");

		Cluster cluster = GlobalInstance.getClusterByClusterId(Integer.parseInt(clusterId));

		String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
				GlobalInstance.timeOfUpdateHostPerformanceSet));

		Map<String, HostPerformance> hostPerformanceMap = new HashMap<String, HostPerformance>();

		if (cluster == null) {
			try {
				cluster = clusterService.getClusterByClusterId(Integer.parseInt(clusterId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
				throw e;
			}
		}

		if (cluster == null) {
			try {
				response.setCharacterEncoding("utf-8");
				Writer out = response.getWriter();
				out.write("该zookeeper集群不存在");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		List<String> serverList = cluster.getServerList();
		for (String server : serverList) {
			String ip = StringUtils.trimToEmpty(server.split(":")[0]);
			hostPerformanceMap.put(ip, GlobalInstance.getHostPerformance(ip));
		}

		model.clear();
		model.put("clusterMap", clusterMap);
		model.put("hostPerformanceMap", hostPerformanceMap);
		model.put("updateTime", updateTime);
		model.put("cluster", cluster);

		return new ModelAndView("host/index", model);
	}

	@RequestMapping(value = "/getClusterByClusterId", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Map<String, Object> getClusterByClusterId(int clusterId) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, HostPerformance> hostPerformanceMap = new HashMap<String, HostPerformance>();
		Cluster cluster = GlobalInstance.getAllClusters().get(clusterId);
		for (String ip : cluster.getServerList()) {
			hostPerformanceMap.put(ip, GlobalInstance.getHostPerformance(ip));
		}

		String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
				GlobalInstance.timeOfUpdateHostPerformanceSet));

		map.put("hostPerformanceMap", hostPerformanceMap);
		map.put("cluster", cluster);
		map.put("updateTime", updateTime);
		return map;
	}

	@ExceptionHandler
	@ResponseBody
	public String handleException(Exception e) {
		return e.getMessage();
	}
}
