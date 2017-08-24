//img preview
var imgPreview = function(event){
	var reader = new FileReader();
	reader.onload = function(){
		$('.pic-profile').attr('src', reader.result)
	};
	reader.readAsDataURL(event.target.files[0]);
};

//=== modal 관련:
/**
 * modal 보여줌
 */
function modalShow(modalIdSel) {
	$('body').removeClass('lock');
	$('.modal').fadeOut();
	$(modalIdSel).fadeIn();
}
/**
 * modal 숨김
 */
function modalHide() {
	$('body').removeClass('lock');
	$('.modal').fadeOut();
}
//=== ]]modal 관련:

$(function(){
	// === modal 관련:
	$('.btn-modal').click(function(e){
		e.preventDefault();
		var target = $(this).attr('href');
		$('body').addClass('lock');
		$('.modal').fadeOut();
		$(target).fadeIn();
	});
	$('.modal .bg, .btn-close').click(function(){
		$('body').removeClass('lock');
		$('.modal').fadeOut();
	});
	// === ]]modal 관련:

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

	//tab
	$('.tab a').click(function(e){
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
	$('.custom-select select').change(function(){
		var txt = $(this).find('option:selected').text();
		$(this).prev('input:text').val(txt);
	});

	//mypage swipe
	var portfolioSwipe = new Swiper('.portfolio-slide', {
        slidesPerView: 4,
        spaceBetween: 23,
        nextButton: '.portfolio-next',
    	prevButton: '.portfolio-prev'
    });
    var favoriteSwipe = new Swiper('.favorite-slide', {
        slidesPerView: 4,
        spaceBetween: 23,
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

	var item = $('.topic-wrap').find('.topic').length
	if(item > 6){
		$('.topic-container').find('button').show();

		var topicSwipe = new Swiper('.topic-slide', {
	        slidesPerView: 6,
	        spaceBetween: 7,
	        simulateTouch: false,
	        nextButton: '.btn-topicNext',
	    	prevButton: '.btn-topicPrev'
	    });
	} else{
		$('.topic-container').find('button').hide();
	}

    //file url
    $('.file input:file').change(function(){
		$(this).parent().prev().find('input:text').val($(this).val().replace(/(c:\\)*fakepath/i, ''));
	});

	//request
	$('.request-list dt').click(function(){
		var parent = $(this).parents('li')
		if(parent.hasClass('active')){
			parent.removeClass('active');
		} else{
			parent.addClass('active').siblings().removeClass('active');
		}
	});

	//my project
	var myProject = new Swiper('.my-project', {
        scrollbar: '.swiper-scrollbar',
        direction: 'vertical',
        slidesPerView: 'auto',
        mousewheelControl: true,
        freeMode: true,
        scrollbarHide: false
    });

    var purchase = new Swiper('.purchase-list', {
        slidesPerView: 4,
        slidesPerColumn: 2,
        spaceBetween: 14,
        simulateTouch: false,
        nextButton: '.purchase-next',
    	prevButton: '.purchase-prev'
    });

    var best = new Swiper('.best-inner', {
        //slidesPerView: 4,
        //slidesPerColumn: 2,
        //spaceBetween: 19,
        simulateTouch: false,
        nextButton: '.purchase-next',
    	prevButton: '.purchase-prev'
    });

});