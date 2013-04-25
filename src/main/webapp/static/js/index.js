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

	$('#contact').popover({
		placement : 'bottom',
		content : '作者：詹益峰 qq：758297924'
	});
});