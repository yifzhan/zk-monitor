$(function() {
	$('#cluster').change(function() {
		$('tbody').empty();
		var clusterId = $(this).val();
		$.ajax({
			url : 'host/getClusterByClusterId',
			type : 'POST',
			dataType : 'json',
			data : {
				clusterId : clusterId
			},
			success : function(data) {
				var table = $('tbody')
				$.each(data.hostPerformanceMap, function(key, value) {
					var ip = '<td>' + key + '</td>';
					var cpuUsage = '<td></td>';
					var memUsage = '<td></td>';
					var load = '<td></td>';
					var diskUsage = '<td></td>';
					if (value) {
						cpuUsage = '<td style="text-align: right;">' + value.cpuUsage + '</td>';
						memUsage = '<td style="text-align: right;">' + value.memUsage + '</td>';
						load = '<td style="text-align: right;">' + value.load + '</td>';
//						diskUsage = '<td style="text-align: right;">' + value.diskUsageMap + '</td>';
						 diskUsage = '<td style="text-align: right;">';
						$.each(value.diskUsageMap, function(mountedOn, usage) {
							diskUsage += mountedOn + '=' + usage + ', ';
						});
						diskUsage = diskUsage.substring(0, diskUsage.length) + '</td>';
					} else {
						ip = '<td style="color: red;">' + key + '</td>'
					}
					var row = '<tr>' + ip + cpuUsage + memUsage + load + diskUsage + '</tr>';
					table.append(row);
				});
				$('#clusterDesc').html(data.cluster.description);
				$('#updateTime').html('更新时间：' + data.updateTime);
			}
		})
	})
});