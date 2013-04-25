package com.zhanyf.hadoop.zk.monitor.job.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.zhanyf.hadoop.zk.domain.Cluster;
import com.zhanyf.hadoop.zk.exception.ServiceException;
import com.zhanyf.hadoop.zk.monitor.GlobalInstance;
import com.zhanyf.hadoop.zk.service.ClusterService;

public class ZkConfigDBToMemDumpTask implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZkConfigDBToMemDumpTask.class);

	@Override
	public void run() {
		try {
			WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
			ClusterService clusterService = ctx.getBean(ClusterService.class);
			List<Cluster> clusters = clusterService.getAllClusters();

			if (clusters == null || clusters.isEmpty()) {
				LOGGER.warn("暂时没有集群");
			} else {
				GlobalInstance.clearClusterMap();
				for (Cluster cluster : clusters) {
					GlobalInstance.putClusterMap(cluster.getClusterId(), cluster);
				}
			}
			GlobalInstance.firstDumpLatch.countDown();

		} catch (ServiceException e) {
			LOGGER.error("Error when dump zookeeper cluster config info to memeory: " + e.getMessage(), e);
		} catch (Throwable e) {
			LOGGER.error("Error when dump zookeeper cluster config info to memeory: " + e.getMessage(), e);
		}

	}

}
