package com.zhanyf.hadoop.zk.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPoolManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolManager.class);

	private static int ZK_SERVER_STATUS_COLLECT_EXEC_THREAD_SIZE = 3;
	private static int ZK_SERVER_PERFORMANCE_COLLECT_EXEC_THREAD_SIZE = 3;
	private static int ZK_NODE_ALIVE_CHECK_EXEC_THREAD_SIZE = 5;
	private static int ZK_CLUSTER_CONFIG_DUMP_EXEC_THREAD_SIZE = 2;

	public static void init() {
		if (zkClusterConfigDumpExec == null) {
			LOGGER.info("starting initiating ThreadPoolManager...");
			zkClusterConfigDumpExec = Executors.newFixedThreadPool(ZK_CLUSTER_CONFIG_DUMP_EXEC_THREAD_SIZE);
			zkNodeAliveCheckExec = Executors.newFixedThreadPool(ZK_NODE_ALIVE_CHECK_EXEC_THREAD_SIZE);
			zkServerPerformanceCollectExec = Executors
					.newFixedThreadPool(ZK_SERVER_PERFORMANCE_COLLECT_EXEC_THREAD_SIZE);
			zkServerStatusCollectExec = Executors.newFixedThreadPool(ZK_SERVER_STATUS_COLLECT_EXEC_THREAD_SIZE);
			LOGGER.info("start ThreadPoolManager successfully");
		}
	}

	private static ExecutorService zkClusterConfigDumpExec;

	public static void addTaskToZkClusterConfigDumpExec(Runnable task) {
		init();
		zkClusterConfigDumpExec.execute(task);
	}

	private static ExecutorService zkNodeAliveCheckExec;

	public static void addTaskToZkNodeAliveCheckExec(Runnable task) {
		init();
		zkNodeAliveCheckExec.execute(task);
	}

	private static ExecutorService zkServerPerformanceCollectExec;

	public static void addTaskToZkServerPerformanceCollectExec(Runnable task) {
		init();
		zkServerPerformanceCollectExec.execute(task);
	}

	private static ExecutorService zkServerStatusCollectExec;

	public static void addTaskToZkServerStatusCollectExec(Runnable task) {
		init();
		zkServerStatusCollectExec.execute(task);
	}
}
