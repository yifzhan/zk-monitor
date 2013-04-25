package com.zhanyf.hadoop.zk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanyf.hadoop.zk.dao.SettingsDao;
import com.zhanyf.hadoop.zk.domain.Settings;

@Service
public class SettingsService {

	@Autowired
	private SettingsDao settingsDao;

	public void saveSettings(Settings settings) {
		settingsDao.saveSettings(settings);
	}
}
