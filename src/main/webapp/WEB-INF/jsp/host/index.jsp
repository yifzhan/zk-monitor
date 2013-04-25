<%@ page language="java" contentType="text/html; csharset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<base href="<%=basePath%>">
<link type="text/css" rel="stylesheet" href="static/bootstrap/css/bootstrap.css">
<script type="text/javascript" src="static/jquery.js"></script>
<script type="text/javascript" src="static/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="static/js/host/index.js"></script>
</head>
<body>
	<div class="container-fluid" style="margin-top: 20px;">
		<div style="text-align: center; font-size: 20px;">Zookeeper 集群主机运行状态</div>
		<div style="padding-top: 10px;" id="updateTime">更新时间：${updateTime }</div>

		<div style="padding-top: 5px;">
			<span> <select id="cluster" type="select" style="width: 150px;">
					<c:forEach items="${clusterMap }" var="item">
						<c:choose>
							<c:when test="${item.key == cluster.clusterId}">
								<option value="${item.key }" selected="selected">${item.value.clusterName }</option>
							</c:when>
							<c:otherwise>
								<option value="${item.key }">${item.value.clusterName }</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select>
			</span><span id="clusterDesc" style="margin-left: 5px;">${cluster.description }</span>
		</div>

		<div>
			<table class="table table-striped table-bordered" style="font-size: 15px;">
				<thead>
					<tr>
						<td>主机IP</td>
						<td>cpu使用率</td>
						<td>内存使用率</td>
						<td>磁盘使用率</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${hostPerformanceMap }" var="item">
						<tr>
							<td>${item.key}</td>
							<td>${item.value.cpuUsage}</td>
							<td>${item.value.menUsage}</td>
							<td>${item.value.load}</td>
					</c:forEach>
					</tr>
				</tbody>
			</table>
			<div class="pagination pagination-right">
				<ul>
					<li><a href="#">Prev</a></li>
					<li><a href="#" class="active">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#">Next</a></li>
				</ul>
			</div>
		</div>
	</div>

</body>
</html>