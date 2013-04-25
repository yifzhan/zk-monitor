package com.zhanyf.hadoop.zk.monitor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.zhanyf.hadoop.zk.dao.SettingsDao;
import com.zhanyf.hadoop.zk.monitor.job.HostPerformanceCollectJob;
import com.zhanyf.hadoop.zk.monitor.job.ZkConfigDBToMemDumpJob;
import com.zhanyf.hadoop.zk.util.SystemConstantUtil;

/**
 * Used to initiate the working threads collecting the status of hosts and zk
 * cluster. This servlet is managed by web container and started when it's
 * deployed
 * <p>
 * 
 * @author zhan yf
 * 
 */
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(InitServlet.class);

	@Override
	public void init() throws ServletException {
		ThreadPoolManager.init();
		initSystemProperties();

		new Thread(new ZkConfigDBToMemDumpJob()).start();

		try {
			// 等待转储完成
			GlobalInstance.firstDumpLatch.await();
		} catch (InterruptedException e) {
			LOGGER.warn("转储数据库集群配置信息到内存时等待线程被中断，转储可能没有成功！", e);
		}
		
		new Thread(new HostPerformanceCollectJob()).start();

		// try {
		// TimeUnit.SECONDS.sleep(5);
		// } catch (InterruptedException e) {
		// LOGGER.warn("转储数据库集群配置信息到内存时等待线程被中断，转储可能没有成功！", e);
		// }

	}

	public void initSystemProperties() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("src/main/resources/app.properties"));
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException(e.getCause());
		}

		SystemConstantUtil.driver = StringUtils.defaultIfBlank(properties.getProperty("jdbc.driver"),
				"com.mysql.jdbc.Driver");
		SystemConstantUtil.url = StringUtils.defaultIfBlank(properties.getProperty("jdbc.url"),
				"jdbc:mysql://localhost:3306/zk");
		SystemConstantUtil.username = StringUtils.defaultIfBlank(properties.getProperty("jdbc.username"), "root");
		SystemConstantUtil.password = StringUtils.defaultIfBlank(properties.getProperty("jdbc.password"), "111111");
		SystemConstantUtil.maxActive = Integer.valueOf(StringUtils.defaultIfBlank(
				properties.getProperty("jdbc.maxActive"), "30"));
		SystemConstantUtil.maxIdle = Integer.valueOf(StringUtils.defaultIfBlank(properties.getProperty("jdbc.maxIdle"),
				"10"));
		SystemConstantUtil.maxWait = Integer.valueOf(StringUtils.defaultIfBlank(properties.getProperty("jdbc.maxWait"),
				"1000"));

		SystemConstantUtil.SSH_USERNAME = StringUtils.defaultIfBlank(properties.getProperty("ssh.username"), "hadoop");
		SystemConstantUtil.SSH_PASSWORD = StringUtils.defaultIfBlank(properties.getProperty("ssh.password"), "hadoop");
		SystemConstantUtil.SSH_PORT = Integer.valueOf(StringUtils.defaultIfBlank(properties.getProperty("ssh.port"),
				"22"));

		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		SettingsDao settingsDao = (SettingsDao) ctx.getBean("settingsDao");

	}
}
