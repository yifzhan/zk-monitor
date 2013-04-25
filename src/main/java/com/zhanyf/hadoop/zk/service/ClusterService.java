package com.zhanyf.hadoop.zk.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.zhanyf.hadoop.zk.dao.ClusterDao;
import com.zhanyf.hadoop.zk.domain.Cluster;
import com.zhanyf.hadoop.zk.exception.ServiceException;

@Service
public class ClusterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClusterService.class);

	@Autowired
	private ClusterDao clusterDao;

	public Cluster getClusterByClusterId(int clusterId) throws ServiceException {
		Cluster cluster;
		try {
			cluster = clusterDao.getClusterByClusterId(clusterId);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServiceException("数据库操作异常  data access exception", e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServiceException("未知异常", e);
		}
		if (cluster == null)
			return null;
		String serverListStr = cluster.getServerListStr();
		List<String> serverList = Arrays.asList(serverListStr.split(","));
		cluster.setServerList(serverList);
		return cluster;
	}

	public List<Cluster> getAllClusters() throws ServiceException {
		List<Cluster> clusters;
		try {
			clusters = clusterDao.getAllClusters();
			for (Cluster cluster : clusters) {
				List<String> serverList = Arrays.asList(cluster.getServerListStr().split(","));
				cluster.setServerList(serverList);
			}
			return clusters;
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServiceException("查询失败", e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServiceException("查询失败", e);
		}
	}
}
