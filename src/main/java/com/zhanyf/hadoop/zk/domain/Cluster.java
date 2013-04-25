package com.zhanyf.hadoop.zk.domain;

import java.util.List;

public class Cluster {
	private int clusterId;
	private String clusterName;
	private String description;
	private List<String> serverList;
	private String serverListStr;

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getServerList() {
		return serverList;
	}

	public void setServerList(List<String> serverList) {
		this.serverList = serverList;
	}

	public String getServerListStr() {
		return serverListStr;
	}

	public void setServerListStr(String serverListStr) {
		this.serverListStr = serverListStr;
	}

}
