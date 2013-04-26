package com.zhanyf.hadoop.zk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.zhanyf.hadoop.zk.domain.HostPerformance;
import com.zhanyf.hadoop.zk.exception.SSHException;

/**
 * @author zhan yf
 * 
 */
public class SSH2Util {

	private final static String COMMAND_TOP = "top -b -n 1 | head -5";
	private final static String COMMAND_DF_LH = "df -lh";
	private final static String LOAD_AVERAGE_STRING = "load average: ";
	private final static String CPU_USAGE_STRING = "Cpu(s):";
	private final static String MEM_USAGE_STRING = "Mem:";
	private final static String SWAP_USAGE_STRING = "Swap:";

	/**
	 * 如果检测主机时没有登录成功，返回Null
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 * @throws SSHException
	 */
	public static HostPerformance getHostPerformance(String ip, int port, String username, String password)
			throws SSHException {

		Connection connection = new Connection(ip, port);
		try {
			connection.connect();
			boolean isAuthenticated = connection.authenticateWithPassword(username, password);
			if (isAuthenticated == false) {
				throw new SSHException("ssh登录失败，请检查帐号和密码：" + ip);
			}
			return getHostPerformance(connection);
		} catch (IOException e) {
			throw new SSHException(e.getMessage(), e);
		} catch (Exception e) {
			throw new SSHException(e.getMessage(), e);

		} finally {
			if (connection != null)
				connection.close();
		}

	}

	private static HostPerformance getHostPerformance(Connection connection) throws IOException {
		HostPerformance hostPerformance = new HostPerformance();
		Session session = null;
		BufferedReader reader = null;

		try {
			session = connection.openSession();
			session.execCommand(COMMAND_TOP);
			reader = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())));

			String totalMem = StringUtils.EMPTY;
			String freeMem = StringUtils.EMPTY;
			String buffersMem = StringUtils.EMPTY;
			String cachedMem = StringUtils.EMPTY;

			String line;
			int lineNum = 0;
			while ((line = reader.readLine()) != null) {
				if (StringUtils.isBlank(line))
					continue;

				lineNum++;

				if (lineNum > 5)
					return hostPerformance;

				if (lineNum == 1) {
					// top命令返回的第一行数据
					// top - 11:10:58 up 2:02, 1 user, load average: 0.16, 0.03,
					// 0.01
					int loadAverageIndex = line.indexOf(LOAD_AVERAGE_STRING);
					String loadAverages = line.substring(loadAverageIndex).replace(LOAD_AVERAGE_STRING, "").trim();
					String[] loadAverageArray = loadAverages.split(",");
					if (loadAverageArray.length != 3)
						continue;
					hostPerformance.setLoad(StringUtils.trimToEmpty(loadAverageArray[0]));
				} else if (lineNum == 3) {
					// top 命令第三行数据
					// Cpu(s): 0.0%us, 0.0%sy, 0.0%ni, 99.9%id, 0.1%wa, 0.0%hi,
					// 0.0%si, 0.0%st
					String cpuUsage = line.split(",")[0].replace(CPU_USAGE_STRING, "").replace("us", "").trim();
					hostPerformance.setCpuUsage(cpuUsage);
				} else if (lineNum == 4) {
					// 第四行数据，用于内存使用情况
					// Mem: 1762040k total, 361664k used, 1400376k free, 17848k
					// buffers
					// 计算公式 realused = total - free - buffers - cached
					String[] memArray = line.replace(MEM_USAGE_STRING, StringUtils.EMPTY).split(",");
					totalMem = StringUtils.trimToEmpty(memArray[0].replace("total", StringUtils.EMPTY).replace("k",
							StringUtils.EMPTY));
					freeMem = StringUtils.trimToEmpty(memArray[2].replace("free", StringUtils.EMPTY).replace("k",
							StringUtils.EMPTY));
					buffersMem = StringUtils.trimToEmpty(memArray[3].replace("buffers", StringUtils.EMPTY).replace("k",
							StringUtils.EMPTY));
				} else if (lineNum == 5) {
					// 交换区数据，cached数据用于计算内存使用率
					// Swap: 3801080k total, 0k used, 3801080k free, 98712k
					// cached
					cachedMem = StringUtils.trimToEmpty(line.split(",")[3].replace("cached", StringUtils.EMPTY)
							.replace("k", StringUtils.EMPTY));

					if (StringUtils.isBlank(totalMem) || StringUtils.isBlank(freeMem)
							|| StringUtils.isBlank(buffersMem) || StringUtils.isBlank(cachedMem)) {
						hostPerformance.setMemUsage("统计异常");
						throw new IllegalStateException("内存统计时存在异常数据");
					}

					try {
						double total = Double.parseDouble(totalMem);
						double free = Double.parseDouble(freeMem);
						double buffers = Double.parseDouble(buffersMem);
						double cached = Double.parseDouble(cachedMem);
						double memUsage = (total - free - buffers - cached) / total;
						hostPerformance.setMemUsage(String.format("%.2f", memUsage * 100) + "%");
					} catch (NumberFormatException e) {
						hostPerformance.setMemUsage("内存统计时存在异常数据");
						throw e;
					}
				} else {
					continue;
				}
			}

			Map<String, String> diskUsageMap = new HashMap<String, String>();
			session = connection.openSession();
			session.execCommand(COMMAND_DF_LH);
			reader = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout())));

			boolean isFirstLine = true;
			while ((line = reader.readLine()) != null) {

				if (StringUtils.isBlank(line))
					continue;

				if (isFirstLine) {
					isFirstLine = false;
					continue;
				}

				line = line.replaceAll("\\s{1,}", ",");
				String[] items = line.split(",");
				if(items.length != 6)
					continue;
				String usage = StringUtils.trimToEmpty(items[4]);
				String mountedOn = StringUtils.trimToEmpty(items[5]);
				diskUsageMap.put(mountedOn, usage);
			}

			hostPerformance.setDiskUsageMap(diskUsageMap);

		} catch (IOException e) {
			throw e;
		} finally {
			if (reader != null)
				reader.close();
			if (session != null)
				session.close();
		}

		return hostPerformance;
	}
}
