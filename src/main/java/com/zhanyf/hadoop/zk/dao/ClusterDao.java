package com.zhanyf.hadoop.zk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.zhanyf.hadoop.zk.domain.Cluster;

@Repository
public interface ClusterDao {

	@Select("select *, serverList as serverListStr from cluster where clusterId=#{clusterId}")
	public Cluster getClusterByClusterId(@Param(value = "clusterId") int clusterId);
	
	@Select("select clusterId, clusterName, serverList as serverListStr, description from cluster")
	public List<Cluster> getAllClusters();
}
