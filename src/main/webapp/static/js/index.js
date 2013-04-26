$(function() {
	$('#mainPanel').load(function() {
		$(this).height($(this).contents().height());
	});

	$('#menu>li>a').click(function() {
		$('#menu>li').removeClass('active');
		$(this).closest('li').addClass('active');

		var url = $(this).attr('url');
		url = url == undefined ? '' : url;
		$('#mainPanel').attr('src', url);
	});

	$('#contact').click(function() {
		$('#contact_info').modal({
			show : true
		});
	});

});