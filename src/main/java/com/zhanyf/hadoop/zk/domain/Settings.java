package com.zhanyf.hadoop.zk.domain;

public class Settings {
	private int id;
	private String envName;
	private int maxThreadsOfZooKeeperCheck;
	private String description;

	public Settings() {
	}

	public Settings(int id, String envName, int maxThreadsOfZooKeeperCheck, String description) {
		this.id = id;
		this.envName = envName;
		this.maxThreadsOfZooKeeperCheck = maxThreadsOfZooKeeperCheck;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public int getMaxThreadsOfZooKeeperCheck() {
		return maxThreadsOfZooKeeperCheck;
	}

	public void setMaxThreadsOfZooKeeperCheck(int maxThreadsOfZooKeeperCheck) {
		this.maxThreadsOfZooKeeperCheck = maxThreadsOfZooKeeperCheck;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
