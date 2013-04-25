package com.zhanyf.hadoop.zk.util;

public class SystemConstantUtil {

	public static String driver;
	public static String url;
	public static String username;
	public static String password;

	public static int maxActive;
	public static int maxIdle;
	public static int maxWait;

	public static String SSH_USERNAME;
	public static String SSH_PASSWORD;
	public static int SSH_PORT;

	// 转储集群信息到内存的间隔时间
	public static final int MINS_RATE_OF_DUMP_ZOOKEEPER_CLUSTER = 1;
	// 监控集群机器运行状态的时间间隔
	public final static int MINS_RATE_OF_COLLECT_HOST_PERFORMANCE = 2;
}
