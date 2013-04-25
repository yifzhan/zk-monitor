package com.zhanyf.hadoop.zk.monitor.job;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhanyf.hadoop.zk.monitor.ThreadPoolManager;
import com.zhanyf.hadoop.zk.monitor.job.task.ZkConfigDBToMemDumpTask;
import com.zhanyf.hadoop.zk.util.SystemConstantUtil;

public class ZkConfigDBToMemDumpJob implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZkConfigDBToMemDumpJob.class);

	@Override
	public void run() {
		while (true) {
			ThreadPoolManager.addTaskToZkClusterConfigDumpExec(new ZkConfigDBToMemDumpTask());
			try {
				TimeUnit.MINUTES.sleep(SystemConstantUtil.MINS_RATE_OF_DUMP_ZOOKEEPER_CLUSTER);
			} catch (InterruptedException e) {
				LOGGER.warn("等待下次转储中断", e);
			} catch (Throwable e) {
				LOGGER.error("集群配置转储到内存失败", e);
			}
		}
	}

}
