package com.zhanyf.hadoop.zk.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhanyf.hadoop.zk.domain.Settings;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SettingsDaoTest {
	@Autowired
	private SettingsDao settingsDao;

	@Before
	public void setUp() {
		assertNotNull(settingsDao);
	}

	@Test
	public void testSaveSettings() {
		Settings settings = new Settings();
		settings.setEnvName("dev");
		settings.setMaxThreadsOfZooKeeperCheck(5);
		settings.setDescription("test settings");
		settingsDao.saveSettings(settings);
	}

	@Test
	public void testGetSettings() {
		Settings settings = new Settings();
		settings.setEnvName("dev");
		settings.setMaxThreadsOfZooKeeperCheck(5);
		settings.setDescription("test settings");
		settingsDao.saveSettings(settings);
		Settings settings2 = settingsDao.getSettings(settings.getId());
		assertEquals("dev", settings2.getEnvName());
		assertEquals(5, settings2.getMaxThreadsOfZooKeeperCheck());
		assertEquals("test settings", settings2.getDescription());
	}

	@Test
	public void testUpdateSettings() {
		Settings settings = new Settings();
		settings.setEnvName("dev");
		settings.setMaxThreadsOfZooKeeperCheck(5);
		settings.setDescription("test settings");
		settingsDao.saveSettings(settings);
		Settings settings2 = settingsDao.getSettings(settings.getId());
		settings2.setDescription("for update");
		settingsDao.updateSettings(settings2);
		Settings settings3 = settingsDao.getSettings(settings.getId());
		assertEquals("for update", settings3.getDescription());
	}

}
