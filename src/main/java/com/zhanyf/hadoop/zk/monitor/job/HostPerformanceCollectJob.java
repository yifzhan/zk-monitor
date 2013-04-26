package com.zhanyf.hadoop.zk.monitor.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.zhanyf.hadoop.zk.domain.Cluster;
import com.zhanyf.hadoop.zk.exception.ServiceException;
import com.zhanyf.hadoop.zk.monitor.GlobalInstance;
import com.zhanyf.hadoop.zk.monitor.ThreadPoolManager;
import com.zhanyf.hadoop.zk.monitor.job.task.HostPerformanceCollectTask;
import com.zhanyf.hadoop.zk.service.ClusterService;
import com.zhanyf.hadoop.zk.util.SystemConstantUtil;

public class HostPerformanceCollectJob implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(HostPerformanceCollectJob.class);

	@Override
	public void run() {
		while (true) {
			if (GlobalInstance.need_host_performance_collect == false) {
				LOGGER.info("不需要监控集群机器运行状态，");
				try {
					TimeUnit.SECONDS.sleep(SystemConstantUtil.MINS_RATE_OF_COLLECT_HOST_PERFORMANCE);
				} catch (InterruptedException e) {
					LOGGER.error(e.getMessage(), e);
				}
				continue;
			}

			WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
			ClusterService clusterService = ctx.getBean(ClusterService.class);

			List<Cluster> clusters = new ArrayList<Cluster>();
			Map<Integer, Cluster> clusterMap = GlobalInstance.getAllClusters();
			try {
				if (!clusterMap.isEmpty())
					clusters.addAll(clusterMap.values());
				else {
					clusters = clusterService.getAllClusters();
				}

				if (clusters.isEmpty()) {
					LOGGER.warn("当前没有集群需要监控");
				} else {
					// 所有机器总数
					int count = 0;
					for (Cluster cluster : clusters) {
						if (cluster != null && cluster.getServerList() != null) {
							count += cluster.getServerList().size();
						}
					}

					// 用于等待集群所有机器检测完毕
					final CountDownLatch latch = new CountDownLatch(count);
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								LOGGER.info("Start all cluster HostPerformanceEntity collect");
								GlobalInstance.timeOfUpdateHostPerformanceSet = new Date().getTime();
								latch.await();
								LOGGER.info("Finish all cluster HostPerformanceEntity collect");
							} catch (InterruptedException e) {
								LOGGER.error(e.getMessage(), e);
							}

						}
					}).start();

					for (Cluster cluster : clusters) {
						if (cluster != null && cluster.getServerList() != null) {

							for (String server : cluster.getServerList()) {
								server = StringUtils.trimToEmpty(server);
								if (StringUtils.isBlank(server))
									continue;
								String ip = server.split(":")[0];
								ThreadPoolManager
										.addTaskToZkServerPerformanceCollectExec(new HostPerformanceCollectTask(ip,
												null, cluster, latch));
							}
						}
					}

				}

				try {
					TimeUnit.MINUTES.sleep(SystemConstantUtil.MINS_RATE_OF_COLLECT_HOST_PERFORMANCE);
				} catch (InterruptedException e) {
					LOGGER.error(e.getMessage(), e);
				}
			} catch (ServiceException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
