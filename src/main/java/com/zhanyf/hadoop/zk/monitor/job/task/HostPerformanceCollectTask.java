package com.zhanyf.hadoop.zk.monitor.job.task;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhanyf.hadoop.zk.domain.AlarmSettings;
import com.zhanyf.hadoop.zk.domain.Cluster;
import com.zhanyf.hadoop.zk.domain.HostPerformance;
import com.zhanyf.hadoop.zk.exception.SSHException;
import com.zhanyf.hadoop.zk.monitor.GlobalInstance;
import com.zhanyf.hadoop.zk.util.SSH2Util;
import com.zhanyf.hadoop.zk.util.SystemConstantUtil;

public class HostPerformanceCollectTask implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(HostPerformanceCollectTask.class);

	private String ip;
	private AlarmSettings alarmSettings;
	private Cluster cluster;
	private final CountDownLatch latch;

	public HostPerformanceCollectTask(String ip, AlarmSettings alarmSettings, Cluster cluster, CountDownLatch latch) {
		this.ip = ip;
		this.alarmSettings = alarmSettings;
		this.cluster = cluster;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			HostPerformance hostPerformance = SSH2Util.getHostPerformance(ip, SystemConstantUtil.SSH_PORT,
					SystemConstantUtil.SSH_USERNAME, SystemConstantUtil.SSH_PASSWORD);
			GlobalInstance.putHostPerformance(ip, hostPerformance);
			LOGGER.info("HostPerformanceEntity collect of #" + cluster.getClusterName() + "-" + ip);
		} catch (SSHException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Throwable t) {
			LOGGER.error(t.getMessage(), t);
		} finally {
			latch.countDown();
		}
	}
}
