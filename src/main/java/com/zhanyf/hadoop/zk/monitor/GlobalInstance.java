package com.zhanyf.hadoop.zk.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import com.zhanyf.hadoop.zk.domain.Cluster;
import com.zhanyf.hadoop.zk.domain.HostPerformance;

public class GlobalInstance {
	// 服务器启动时转储集群配置信息
	public static final CountDownLatch firstDumpLatch = new CountDownLatch(1);

	/** 是否需要进行机器状态收集 */
	public static boolean need_host_performance_collect = true;

	// 集群map
	private static Map<Integer, Cluster> clusterMap = new ConcurrentHashMap<Integer, Cluster>();

	// 主机运行状态
	private static Map<String, HostPerformance> hostPerformanceMap = new ConcurrentHashMap<String, HostPerformance>();

	// 集群机器状态更新时间
	public static long timeOfUpdateHostPerformanceSet;

	public static Cluster getClusterByClusterId(int clusterId) {
		return clusterMap.get(clusterId);
	}

	public static HostPerformance getHostPerformance(String ip) {
		return hostPerformanceMap.get(ip);
	}

	public static Map<Integer, Cluster> getAllClusters() {
		return clusterMap;
	}

	public static void clearClusterMap() {
		clusterMap.clear();
	}

	public static void putClusterMap(int clusterId, Cluster cluster) {
		clusterMap.put(clusterId, cluster);
	}

	public static void putHostPerformance(String ip, HostPerformance hostPerformance) {
		hostPerformanceMap.put(ip, hostPerformance);
	}

}
