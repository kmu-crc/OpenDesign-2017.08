/**
 * jsp에 쓸것(ui.js에서 변경)
 */

/**
 * ajax global setting:
 */ 
$(function(){
	$( document ).ajaxStart(function() {
		console.log( "Triggered ajaxStart handler. show loading layer." );
		$('.wrap-loading').show();
	});
	$( document ).ajaxStop(function() {
		console.log( "Triggered ajaxStop handler. hide loading layer. " );
		$('.wrap-loading').hide();
	});
	$( document ).ajaxError(function( event, jqXHR, ajaxSettings, thrownError ) {
		console.log( "Error requesting page " + ajaxSettings.url ); 
	});
	$.ajaxSetup({
		cache: false
	});
	
});

//img preview
var imgPreview = function(event){
	var reader = new FileReader();
	reader.onload = function(){
		$('.pic-profile').attr('src', reader.result)
	};
	reader.readAsDataURL(event.target.files[0]);
};

//=== modal 관련: 최현철 : modal.jsp로 이동
/**
 * modal 보여줌
 */
//function modalShow(modalIdSel) {
//	$('body').removeClass('lock');
//	$('.modal').fadeOut();
//	$(modalIdSel).fadeIn();
//}
/**
 * modal 숨김
 */
//function modalHide() {
//	$('body').removeClass('lock');
//	$('.modal').fadeOut();
//}
//=== ]]modal 관련:

$(function(){
	/**
	 * 공통으로 처리하는 event를 delegate 방식으로 바꾼다. 새로 생성된 element도 같이 처리되도록 하기위해.
	 * swipe는 따로 처리해야 한다.
	 */
	
	// === modal 관련:
	//$('.btn-modal').click(function(e){
	//	e.preventDefault();
	//	var target = $(this).attr('href');
	//	$('body').addClass('lock');
	//	$('.modal').fadeOut();
	//	$(target).fadeIn();
	//});

//  modal 관련: 최현철 : modal.jsp로 이동	
//	$('body').on('click', '.modal .bg, .btn-close', function(){ //$('.modal .bg, .btn-close').click(function(){ 
//		$('body').removeClass('lock');
//		$('.modal').fadeOut();
//	});
	
	// === ]]modal 관련:

	//nav
	$('body').on({ //$('.nav-cate > ul > li').bind({
		mouseenter: function(){
			$(this).find('.cate-sub').show();
		},
		mouseleave: function(){
			$(this).find('.cate-sub').hide();
		}
	}, '.nav-cate > ul > li');
	
	$('body').on({ //$('.cate-sub li').bind({
		mouseenter: function(){
			$(this).addClass('active')
		},
		mouseleave: function(){
			$(this).removeClass('active')
		}
	}, '.cate-sub li');

	//tab
	$('body').on('click', '.tab a', function(e){ //$('.tab a').click(function(e){
		e.preventDefault();
		var target = $(this).attr('href');
		$(this).parent().addClass('active').siblings().removeClass('active')
		$(target).addClass('active').siblings().removeClass('active')
	});

	//select
	$('.custom-select select').each(function(){
		var txt = $(this).find('option:selected').text();
		$(this).prev('input:text').val(txt);
	});
	/**
	 * custom-select input readonly
	 */
	$('.custom-select input[type="text"]').prop('readonly',true);
	$('body').on('change', '.custom-select select', function(){//$('.custom-select select').change(function(){
		var txt = $(this).find('option:selected').text();
		$(this).prev('input:text').val(txt);
	});

	/*
	//mypage swipe
	var portfolioSwipe = new Swiper('.portfolio-slide', {
        slidesPerView: 3,
        spaceBetween: 24,
        nextButton: '.portfolio-next',
    	prevButton: '.portfolio-prev'
    });
    var favoriteSwipe = new Swiper('.favorite-slide', {
        slidesPerView: 3,
        spaceBetween: 24,
        nextButton: '.favorite-next',
    	prevButton: '.favorite-prev'
    });
    $('.slide').each(function(){
    	var item = $(this).find('li').length
    	if(item > 3){
    		$(this).siblings('.slide-btn').show();
    	} else{
    		$(this).siblings('.slide-btn').hide();
    	}
    });
    
  
    var purchase = new Swiper('.purchase-list', {
        slidesPerView: 4,
        slidesPerColumn: 2,
        spaceBetween: 14,
        simulateTouch: false,
        nextButton: '.purchase-next',
    	prevButton: '.purchase-prev'
    });
    */

// move to << project_list : swipeInit >>  
//	var item = $('.topic-wrap').find('.topic').length;
//	if(item > 6){
//		$('.topic-container').find('button').show();
//
//		var topicSwipe = new Swiper('.topic-slide', {
//	        slidesPerView: 6,
//	        spaceBetween: 7,
//	        nextButton: '.btn-topicNext',
//	    	prevButton: '.btn-topicPrev'
//	    });
//	} else{
//		$('.topic-container').find('button').hide();
//	}

    //file url
    $('body').on('change', '.file input:file', function(){ //$('.file input:file').change(function(){
		$(this).parent().prev().find('input:text').val($(this).val().replace(/(c:\\)*fakepath/i, ''));
	});

	//request
    $('body').on('click', '.request-list dt', function(){ //$('.request-list dt').click(function(){
		var parent = $(this).parents('li')
		if(parent.hasClass('active')){
			parent.removeClass('active');
		} else{
			parent.addClass('active').siblings().removeClass('active');
		}
	});
	
	//=== enter시 자동 commit하는것을 막아준다. 
    //$('body').keydown(function (event) {
        //if(event.keyCode == 13){//키가 13이면 실행 (엔터는 13)
        //	event.preventDefault();
        //	return;
        //}
	 //});
    
    
    //nav
	$('.nav-cate > ul > li').bind({
		mouseenter: function(){
			$(this).addClass('active').find('.depth3').show();
		},
		mouseleave: function(){
			$(this).removeClass('active').find('.depth3').hide();
		}
	});
	
	$('.depth3 li').bind({
		mouseenter: function(){
			$(this).addClass('active').find('.depth4').show();
		},
		mouseleave: function(){
			$(this).removeClass('active').find('.depth4').hide();
		}
	});

});