package com.zhanyf.hadoop.zk.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.zhanyf.hadoop.zk.domain.Settings;

@Repository
public interface SettingsDao {

	@Select(value = "select * from settings where id=#{id}")
	public Settings getSettings(@Param(value = "id") int id);

	@Update(value = "update settings set envName=#{settings.envName}, maxThreadsOfZooKeeperCheck=#{settings.maxThreadsOfZooKeeperCheck}, description=#{settings.description} where id=#{settings.id}")
	public void updateSettings(@Param(value = "settings") Settings settings);

	@Insert("insert into settings(envName, maxThreadsOfZooKeeperCheck, description) values(#{settings.envName}, #{settings.maxThreadsOfZooKeeperCheck}, #{settings.description})")
	@Options(keyProperty = "settings.id", useGeneratedKeys = true)
	public void saveSettings(@Param(value = "settings") Settings settings);
}
