package com.zhanyf.hadoop.zk.domain;

import java.util.Map;

public class HostPerformance {
	private int id;
	private String hostname;
	private String cpuUsage;
	private String load;
	private String memUsage;

	private Map<String, String> diskUsageMap;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public String getMemUsage() {
		return memUsage;
	}

	public void setMemUsage(String memUsage) {
		this.memUsage = memUsage;
	}

	public Map<String, String> getDiskUsageMap() {
		return diskUsageMap;
	}

	public void setDiskUsageMap(Map<String, String> diskUsageMap) {
		this.diskUsageMap = diskUsageMap;
	}

}
