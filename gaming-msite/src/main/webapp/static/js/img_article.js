(function($){
	//鍥剧墖鍚庤浇
	$.fn.lazyLoadImg = function(setting){
		var defaults = {
			lazySrc:'data-src',	//瀛樻斁瀹為檯鍥剧墖鐨勫湴鍧€锛岀敤娉曪細<img src="绌虹櫧灏忓浘鐗囩殑鍦板潃" data-src="瀹為檯鍥剧墖鍦板潃" />
			blank:'http://img.18183.duoku.com/uploads/wap/yy/bc-loading.gif'	//閫忔槑灏忓浘鐗囩殑榛樿鍦板潃
		}
		setting = $.extend({},defaults,setting);
		return this.each(function(i){
			if(!$(this).attr(setting.lazySrc)){
				return;
			}
			if($(this).attr("src")=='' || $(this).attr("src")==setting.blank){
				$(this).attr("src",$(this).attr(setting.lazySrc));
			}
		});
	};
	//鍥鹃泦鎾斁
	$.fn.atlasPlay = function(setting){
		//鍙傛暟
		defaults = {
			rbox:'.atlas-mod',          //瀹瑰櫒
			tit:'.atlas-title',			//鏍囬
			picmod:'.atlas-img',	    //鍥鹃泦瀹瑰櫒
			oper:'.atlas-operate'		//鎿嶄綔鏉★拷
		};
		setting = $.extend({},defaults,setting);
        return this.each(function(){ 
            var $this = $(this); 
			var $tit=$this.find(setting.tit);
			var $img=$this.find(setting.picmod);
			var $operate=$this.find(setting.oper);
			var $rbox=$this.find(setting.rbox);
			var $imgList=$img.find(".pic-list");
			var $imgPb=$img.find(".pic-alls");
			var $imgUl=$img.find(".pic-list ul");
			var $info=$img.find(".img-info");
        	var num=$imgUl.find("li").length-1;
			var ind=1;//鍥剧墖绱㈠紩鍊�
			var ph;
			var pw=$(window).width();
			var w=pw*($imgUl.find("li").length+10);
			var html;
			var n=2;//n鏄竴琛屾渶澶氱殑li锛屾墍浠ュ皬浜巒灏辨槸绗竴琛�
			var diyc=true;
			var objD;
			var margin = 10;//璁剧疆闂磋窛
			var everyH = [];//瀹氫箟涓€涓暟缁勫瓨鍌ㄦ瘡涓€鍒楃殑楂樺害
			var $box;
			var li;//鍖哄潡
			var ulT=0;//鍋忕Щ璺濈
			var ulZ=0;
			var isScrolling=1;
			var nopic=true;
			if($(".all_hd_full").length>0){
				ph=$(window).height()-42;
				$(".ad-hd-bak").show();
				$tit.css({"top":"42px"});
			}else{
				ph=$(window).height();	
			};
                        if($(".bottomwap_frame").length>0){
				$(".last_big_ad").find(".bottomwap_box").height($(".bottomwap_frame").height());
				$(".last_big_ad").find(".bottomwap_box").append($(".bottomwap_frame").html());
			};
			//鍚姩绉诲姩绔覆鏌�
			var qDrun=function(){
				$imgUl.addClass("");
				$imgUl.css({"-webkit-transform":"translate3d(0,0,0)","transform":"translate3d(0,0,0)","transition-duration":"0s","-webkit-transition-duration":"0s"});
				$tit.find(".image-total span").html(num);
			};
			//灞忓箷璁剧疆
			var setWin=function(){
				if($(".all_hd_full").length>0){
					ph=$(window).height()-42;
				}else{
					ph=$(window).height();	
				};
				pw=$(window).width();
				w=pw*($imgUl.find("li").length+10);
				$imgUl.css({"width":w+"px","height":ph+"px","transition-duration":"0s","-webkit-transition-duration":"0s"});
				$imgUl.find(".last_big_ad").siblings().css("line-height",ph+"px");
				$imgUl.find("li").width(pw+"px");
				$("#atlasplay").height(ph+"px");
				
			};
			//椤垫暟鏄剧ず
			var indCl=function(i){
				if(i>num) i=num;
				$tit.find(".image-count span").html(i+"/"+num);
				var iU=$imgUl.find("li").eq(i-1).find("img").attr("data-src");
				$operate.find(".btn-yt").attr("href",iU);
			};
			qDrun();setWin();indCl(ind);
			
			// 鍒ゆ柇鍥剧墖鍔犺浇鐨勫嚱鏁�
			var isImgLoad=function(callback,data){
				var imgdefereds=[];
				data.find("img").each(function(){
					var dfd=$.Deferred();//寤惰繜瀵硅薄
					$(this).bind('load',function(){
  						dfd.resolve();
 					}).bind('error',function(){
						//鍥剧墖鍔犺浇閿欒锛屽姞鍏ラ敊璇鐞�
						dfd.resolve();
					});
					if(this.complete){
						setTimeout(function(){dfd.resolve();},1000);
					};
					imgdefereds.push(dfd);
				});
				$.when.apply(null,imgdefereds).done(function(){
					callback(data);
				});
			};
			
			//鍒濆鍥炶皟鍑芥暟
			var callback=function(){
				setTimeout(function(){
					$imgUl.addClass("pics_transition");
					$imgList.show();
					$info.addClass("fadeInShow");
					$tit.addClass("animated "+$tit.attr("data-animated"));
					$operate.addClass("animated "+$operate.attr("data-animated"));
				},100);
			};
			
			//鍋忕Щ
			var shifting=function(time,st){
				$imgUl.css({"-webkit-transform":"translate3d("+st+"px,0,0)","transform":"translate3d("+st+"px,0,0)","transition-duration":time+"ms","-webkit-transition-duration":time+"ms"});
				$imgUl.find("li").eq(ind+1).find("img").lazyLoadImg();
				indCl(ind);
			};
			//touchstart浜嬩欢  
			var touchSatrtFunc=function(evt){
			   try  
				{  
					//evt.preventDefault(); //闃绘瑙︽懜鏃舵祻瑙堝櫒鐨勭缉鏀俱€佹粴鍔ㄦ潯婊氬姩绛�  
					var touch = evt.touches[0]; //鑾峰彇绗竴涓Е鐐�  
					var x = Number(touch.pageX); //椤甸潰瑙︾偣X鍧愭爣
					var y = Number(touch.pageY); //椤甸潰瑙︾偣Y鍧愭爣  
					//璁板綍瑙︾偣鍒濆浣嶇疆 
					startX = x;
					startY = y;
				}  
				catch (e) {  
				   alert();  
			   }  
		   	};
		   	//touchmove浜嬩欢锛岃繖涓簨浠舵棤娉曡幏鍙栧潗鏍�
		   	var touchMoveFunc=function(evt) {
			   try  
			   {  
					var touch = evt.touches[0]; //鑾峰彇绗竴涓Е鐐�  
					var x = Number(touch.pageX); //椤甸潰瑙︾偣X鍧愭爣
					var y = Number(touch.pageY); //椤甸潰瑙︾偣Y鍧� 
					var lx=x+10;
					var rx=x-10;
					xp=x-startX;
					yp=y-startY;
					isScrolling = Math.abs(xp) < Math.abs(yp) ? 1:0;//isScrolling涓�1鏃讹紝琛ㄧず绾靛悜婊戝姩锛�0涓烘í鍚戞粦鍔�
					if(isScrolling === 0){
						event.preventDefault();      //闃绘瑙︽懜浜嬩欢鐨勯粯璁よ涓猴紝鍗抽樆姝㈡粴灞�
						//$info.removeClass("fadeInShow").addClass("fadeInHide");
						if(lx<startX){
							ulZ=ulT-Math.abs(xp);
						};
						if(rx>startX){
							ulZ=ulT+Math.abs(xp);
						};
						shifting(0,ulZ);
					};
				}  
			   catch (e) {    
				}  
		   };
			//touchend浜嬩欢  
		    var touchEndFunc=function(evt){
			   try {  
					if(isScrolling === 0){
                                               evt.preventDefault(); //闃绘瑙︽懜鏃舵祻瑙堝櫒鐨勭缉鏀俱€佹粴鍔ㄦ潯婊氬姩绛�  
						if((xp+10)<0){
							ind++;
						};
						if((xp-10)>0){
							ind--;
						};
						ulZ=-(ind-1)*pw;
						shifting(500,ulZ);
						if(ind<1){
							ulZ=0;
							ind=1;
							shifting(500,0);
						}else if(ind>num){
							ulZ=-pw*num;
							ind=num+1;
							shifting(500,ulZ);
							$info.removeClass("fadeInShow").addClass("fadeInHide");
							if(!nopic) $tit.show();
							$operate.hide();
						}else{
							if(nopic){
								$info.removeClass("fadeInHide").addClass("fadeInShow");
								$operate.show();
							}else{
								$tit.hide();
							};
						};
					};
					ulT=ulZ;
					startX = 0;
					startY = 0;
					xp=0;//闃叉鐐瑰嚮绉诲姩
					isScrolling=1;
			   	}  
				catch (e) {  
				};
			};
			//缁戝畾浜嬩欢
			$imgList.get(0).addEventListener('touchstart', touchSatrtFunc, false);  
			$imgList.get(0).addEventListener('touchmove', touchMoveFunc, false);  
			$imgList.get(0).addEventListener('touchend', touchEndFunc, false);
			
			//棰勫厛鍔犺浇鍓嶄笁寮犲浘鐗�
			for(var i=0;i<3;i++){
				$imgUl.find("li").eq(i).find("img").lazyLoadImg();
				if(i==2) isImgLoad(callback,$imgUl);
			};
        });
    };
})(jQuery)