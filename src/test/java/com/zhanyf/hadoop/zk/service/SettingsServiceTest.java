package com.zhanyf.hadoop.zk.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import com.zhanyf.hadoop.zk.domain.Settings;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SettingsServiceTest {

	@Autowired
	private SettingsService settingsService;

	@Before
	public void setUp() {
		assertNotNull(settingsService);
	}

	@Test
	public void testSaveSettings() {
		Settings settings = new Settings();
		settings.setEnvName("test");
		settings.setMaxThreadsOfZooKeeperCheck(5);
		settings.setDescription("test settings");
	}
}
