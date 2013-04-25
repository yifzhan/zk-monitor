$(function() {
	$('#cluster').change(function() {
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
					var row = '<tr><td>' + key + '</td></tr>'
					table.append(row)
				});
				$('#clusterDesc').html(data.cluster.description);
				$('#updateTime').html('更新时间：' + data.updateTime);
			}
		})

		$('tbody').empty();
	})
});