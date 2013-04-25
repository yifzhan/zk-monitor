package com.zhanyf.hadoop.zk.monitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-servlet.xml" })
public class InitServletTest {

	@Test
	public void testInitSystemProperties() {
		new InitServlet().initSystemProperties();
	}
}
