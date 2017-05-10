

$(function(){
	//导航栏
	$("#wcgp-nav1 li").click(function(){
		$("#wcgp-nav2 li,#wcgp-nav1 li").removeClass("on");
		$(this).addClass("on");
		});
	$("#wcgp-nav2 li").click(function(){
		$("#wcgp-nav2 li,#wcgp-nav1 li").removeClass("on");
		$(this).addClass("on");
	});
	
	
	//导航栏隐藏
	$(".wcgp-nav-more").click(function(){
		if($(".wcgp-navm-con").hasClass("hide")){
			$(".wcgp-navm-con").toggleClass("show");
			$("#wcgp-nav-p1").show();
			$("#nav-bg").toggleClass("change")
		};
		if($(".wcgp-navm-con").hasClass("show")){
			$("#wcgp-nav-p1").hide();
			$("#wcgp-nav-p2").show();
		};
	});																																																																																																																
																																																																																																					
	
	})
	
	


















































































