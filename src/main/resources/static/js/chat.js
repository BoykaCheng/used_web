$('.conLeft li').on('click',function(){
		$(this).addClass('bg').siblings().removeClass('bg');
		var intername=$(this).children('.liRight').children('.intername').text();
		$('.headName').text(intername);
		$('.newsList').html('');
	});

	function answers(){
		var arr=["你好","今天天气很棒啊","你吃饭了吗？","我最美我最美","我是可爱的僵小鱼","你们忍心这样子对我吗？","spring天下无敌，实习工资850","我不管，我最帅，我是你们的小可爱","段友出征，寸草不生","一入段子深似海，从此节操是路人","馒头：嗷","突然想开个车","段子界混的最惨的两个狗：拉斯，普拉达。。。"];
		var aa=Math.floor((Math.random()*arr.length));
		var answer='';
		answer+='<li>'+
					'<div class="answerHead"><img src="/img/msg_box/tou.jpg"/></div>'+
					'<div class="answers"><img class="jiao" src="/img/msg_box/jiao.jpg">'+arr[aa]+'</div>'+
				'</li>';
		$('.newsList').append(answer);	
		$('.RightCont').scrollTop($('.RightCont')[0].scrollHeight );
	}
	$('.ExP').on('mouseenter',function(){
		$('.emjon').show();
	})
	$('.emjon').on('mouseleave',function(){
		$('.emjon').hide();
	})
	$('.emjon li').on('click',function(){
		var imgSrc=$(this).children('img').attr('src');
		var str="";
		str+='<li>'+
				'<div class="nesHead"><img src="/img/msg_box/6.jpg"/></div>'+
				'<div class="news"><img class="jiao" src="/img/msg_box/20170926103645_03_02.jpg"><img class="Expr" src="'+imgSrc+'"></div>'+
			'</li>';
		$('.newsList').append(str);
		$('.emjon').hide();
		$('.RightCont').scrollTop($('.RightCont')[0].scrollHeight );
	})