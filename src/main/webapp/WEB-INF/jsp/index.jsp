<%@ page language="java" contentType="text/html; csharset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>zookeeper集群可视化管理工具</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/static/bootstrap/css/bootstrap.css" />">
<script type="text/javascript" src="<c:url value="/static/jquery.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/bootstrap/js/bootstrap.js" />"></script>
<script type="text/javascript" src='<c:url value="/static/js/index.js" ></c:url>'></script>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="brand" href="javascript:void(0)">ZK Monitor</a>
				<div class="nav-collapse collapse">
					<p class="navbar-text pull-right">
						Logged in as <a href="#" class="navbar-link">Username</a>
					</p>
					<ul class="nav">
						<li class="active"><a href="javascript:void(0)">主页</a></li>
						<li><a href="javascript:void(0)">关于</a></li>
						<li><a href="javascript:void(0)" id="contact">联系我</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container-fluid" style="margin-top: 60px;">
		<div class="row-fluid">
			<div class="span3">
				<div class="well sidebar-nav">
					<ul id="menu" class="nav nav-list">
						<li class="nav-header">监控</li>
						<li class="active"><a href="javascript:void(0)">集群监控</a></li>
						<li><a href="javascript:void(0)" url="host/index">主机监控</a></li>
						<li class="nav-header">设置</li>
						<li><a href="#">集群配置</a></li>
						<li><a href="#">报警设置</a></li>
						<li><a href="#">系统设置</a></li>
						<li><a href="#">报警开关</a></li>
					</ul>
				</div>
				<!--/.well -->
			</div>
			<!--/span-->

			<div class="span8">
				<iframe id="mainPanel" src="cluster/index" width="100%" frameborder="0" scrolling="no" marginwidth="0"
					marginheight="0"></iframe>
			</div>

		</div>
		<hr>

		<footer>
			<div style="margin: 0 auto; text-align: center;">
				<p>&copy; 中国电子科技集团 58所 2013</p>
			</div>
		</footer>

	</div>
</body>
</html>