<%-- 화면ID : OD01-01-02 --%>
<%-- 화면ID : OD01-01-03 --%>
<%-- 화면ID : OD03-02-06 --%>
<%@page import="com.opendesign.utils.PropertyUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- ********************* loading ********************* -->
<style type="text/css" >
	.wrap-loading{ /*화면 전체를 어둡게 합니다.*/
	    position: fixed;
	    z-index:999999999;
	    left:0;
	    right:0;
	    top:0;
	    bottom:0;
	    background: rgba(0,0,0,0.2); /*not in ie */
	    filter: progid:DXImageTransform.Microsoft.Gradient(startColorstr='#20000000', endColorstr='#20000000');    /* ie */
	    
	}
    .wrap-loading div{ /*로딩 이미지*/
        position: fixed;
        z-index:999999999;
        top:50%;
        left:50%;
        margin-left: -21px;
        margin-top: -21px;
    }
</style>
<div class="wrap-loading hide">
    <div><img src="/resources/image/loading/km_loader.gif" /></div>
</div> 
<!-- ********************* ]]loading ********************* -->

<!-- 로그인 -->
<div class="modal" id="login-modal">
	<div class="bg"></div>
	<div class="modal-inner modal-border">
		<h1>로그인</h1>
		<form name="loginForm">
			<fieldset>
				<legend>로그인</legend>
				<input type="text" name="email" placeholder="이메일">
				<input type="password" name="passwd" placeholder="비밀번호" />
				<button type="button" id="btn_modal_login" class="btn btn-red">로그인</button> 
				<span class="division-line"></span>
				<button type="button" id="btn_modal_FB_login" class="btn-facebook"><i class="fa fa-facebook-square" aria-hidden="true"></i> facebook 으로 로그인하기</button>
				<div class="btn-set">
					<a href="javascript:requestCaptCha();modalShow('#pw-modal');" class="btn-find">비밀번호 찾기</a>
					<a href="javascript:modalShow('#join-modal');" class="btn-modal">회원가입</a>
				</div>
			</fieldset>
		</form>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>
<script>
$(function(){
	$('body').on('click', '.modal .bg, .btn-close', function(){ //$('.modal .bg, .btn-close').click(function(){ 
		modalHide();
		
	});
});
	
/**
 * modal 보여줌
 */
function modalShow(modalIdSel, opts) {
	$('body').removeClass('lock');
	$('.modal').fadeOut();
	var modalObj = $(modalIdSel); 
	modalObj.fadeIn();
	$('html, body').css({'overflow': 'hidden', 'height': '100%'});
	$('#element').on('scroll mousewheel', function(event) { 
		event.preventDefault();
		event.stopPropagation();     
		return false; 
	});
	
	/* 이벤트 처리 */
	if( '#login-modal' == modalIdSel ){
		var onSuccessLogin = null;
		if( opts && opts.onSuccessLogin ){
			onSuccessLogin = opts.onSuccessLogin;
		}
		
		var btnLogin = $('#btn_modal_login');
		btnLogin.off(); //unbind previous event
		btnLogin.on('click', function(e){
		
			loginFormSubmit( onSuccessLogin );
			
		});
		
		var btnFBLogin = $('#btn_modal_FB_login');
		btnFBLogin.off(); //unbind previous event
		btnFBLogin.on('click', function(e){
			
			facebooklogin( onSuccessLogin );
			
		});
		
		$('input[name="passwd"]').on('keyup', function(){
			if (event.keyCode == 13){
				$('#btn_modal_login').click();
			}
		});
		
	}else if( '#project-search' == modalIdSel ){
		modalObj.find('.search-list ul').empty();
		
		var schProjectName = modalObj.find('input[name="schProjectName"]');
		schProjectName.on('keyup', function(e){
			var charCode = (window.event) ?  event.keyCode : e.which;
			if(13 == charCode) {
				searchGroupProject(opts);							
			}
		});
		schProjectName.val('');
		
		var btnComplete = modalObj.find('.btn-complete');
		btnComplete.off();
		btnComplete.on('click', function(e){
			e.preventDefault();
			
			addGroupProject(opts);
			
		});
	}
}



/**
 * modal 숨김
 */
function modalHide() {
	$('body').removeClass('lock');
	$('.modal').fadeOut();
	$('html, body').css({'overflow': 'initial', 'height': '100%'}); 
	$('#element').off('scroll mousewheel');
}

/** 로그인 */
var flag_loginFormSubmit = false; //flag
function loginFormSubmit( onSuccessLogin ) {
	//
	var myForm = $('form[name="loginForm"]');
	if(!myForm.valid()) {
		return;
	}
	
	if(flag_loginFormSubmit) {
		return;
	}
	flag_loginFormSubmit = true;
	
	//== 
	$.ajax({
        url: '/login.ajax',
        type: 'post',
        data: myForm.serialize(),
        complete : function(_data){
			flag_loginFormSubmit = false;
		},
        error : function(_data) {
        	console.log(_data);
			alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	console.log(_data);
        	if(_data.result == '200') {
        		alert("존재하지 않는 이메일이거나 패스워드가 잘못되었습니다."); 
        		myForm.find('[name="email"]').focus();
        	} else if(_data.result == '1') {
        		//성공
        		if( onSuccessLogin ){
        			/* 
        			* 파라미터가 'true' 일 경우에는 
        			* 로그인 프로세스 진행 후, 콜백이 호출 되었다는 의미
        			* --- 현재는 '프로젝트 상세 댓글 등록' 에서만 사용하고 있음---
        			*/
        			onSuccessLogin(true);
        			return;
        		}
        		
        		window.location.reload();
        	} else {
        		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        	}
        }
    });
}

var flag_loginFBSubmit = false; //flag
function loginFBSubmit(onSuccessLogin, fbUser) {
	
	if(flag_loginFBSubmit) {
		return;
	}
	flag_loginFBSubmit = true;
	
	$.ajax({
        url: '/loginFB.ajax',
        type: 'post',
        data: {'email':fbUser.email, 'fbAccessToken':fbUser.token},
        complete : function(_data){
        	flag_loginFBSubmit = false;
		},
        error : function(_data) {
        	console.log(_data);
			alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	console.log(_data);
        	if(_data.result == '1') {
        		//성공
        		if( onSuccessLogin ){
        			/* 
        			* 파라미터가 'true' 일 경우에는 
        			* 로그인 프로세스 진행 후, 콜백이 호출 되었다는 의미
        			* --- 현재는 '프로젝트 상세 댓글 등록' 에서만 사용하고 있음---
        			*/
        			onSuccessLogin(true);
        			return;
        		}
        		
        		window.location.reload();
        	} else {
        		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        	}
        }
    });
	
	
}

function goPage(url, isCheckedLogin){
	if( isCheckedLogin ){
		checkedLogin(function(){
			location.href = url;			
		});
		return;
	}
	location.href = url;
}


//페이스북 로그인 
function facebooklogin( onSuccessLogin ) { 
   //  alert("facebook");
	FB.login(function(response) {  
      
    var accessToken = response.authResponse.accessToken; 
    FB.api('/me', { locale: 'en_US', fields: 'name, email' }, function(user) { 
      
     console.log("fb user.id=" + user.id + ", user.email=" + user.email + ", fbname=" + user.name + ", accessToken=" +accessToken );
     user.token = accessToken;
     
     if( !isEmpty(user.email) && !isEmpty(accessToken) ) {
    	 
    	 $.ajax({
    	        url: '/hasEmailDuplication.ajax',
    	        type: 'get',
    	        data: {'email':user.email}, 
    	        complete : function(_data){},
    	        error : function(_data) {
    				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
    	        },
    	        success : function(_data){
    	        	if( _data.result ) {
    	        		
    	        		/* 중복되는 이메일이 있다면 로그인 처리  */
    	        		loginFBSubmit(onSuccessLogin, user);
    	        		
    	        	} else {
    	        		/*중복되는 이메일이 없다면  신규회원 가입프로세스 처리 */
    	        		regFormSubmit(true, user);
    	        	
    	        	}
    	        }
    	    });
    	 
     } else {
    	 alert("Facebook 정보가 잘못 되었습니다. 다시 로그인해 주세요.")
     }
      
      
    });   
  }, {scope: "user_friends,public_profile,email"});
}


/**
 * 로그인 체크 
 */
var flag_checkedLogin = false; //flag
function checkedLogin( callback ){
	
	if(flag_checkedLogin) {
		return;
	}
	flag_checkedLogin = true;
	
	$.ajax({
        url: '/checkedLogin.ajax',
        type: 'get',
        complete : function(_data){
			flag_checkedLogin = false;
		},
        error : function(_data) {
        	console.log(_data);
			alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	if( _data.result ) {
        		callback();
        	} else {
        		if( confirm('로그인이 필요합니다. 로그인 하시겠습니까?') ){
        			modalShow('#login-modal', {onSuccessLogin:callback});
        			
        		}
        	}
        }
    });
}

//=== 검증 ==========
$(function(){
	loginFormValidRuleInit();
	captchaFormValidRuleInit();
})

function loginFormValidRuleInit(){
	
	var myForm = $('form[name="loginForm"]');
	myForm.validate({
		rules:{
			email : { required: true, email: true }
			, passwd : { required: true }
		},
		messages: {
			email : { required: "필수로 입력해야 합니다.", email: "올바른 이메일형식이 아닙니다." } 
			, passwd : { required: "필수로 입력해야 합니다." }
		},
		showErrors: function(errorMap, errorList) {
	    	if( errorList && errorList.length > 0 ){
	    		for( var i = 0; i < errorList.length; i++ ){                			
					alert(errorList[i].message);
	               	errorList[i].element.focus();
	    			break;
	    		}
	    	}
	    },
	    ignore: [],
	    focusInvalid:false,
	    onfocusout: false,
	    onkeyup: false,
	    onclick: false
	});
}

function captchaFormValidRuleInit(){
	
	var myForm = $('form[name="captchaForm"]');
	myForm.validate({
		rules:{
			captchaEmail : { required: true, email: true }
			, captchaAnswer : { required: true }
		},
		messages: {
			captchaEmail : { required: "이메일을 입력해 주세요.", email: "올바른 이메일형식이 아닙니다." } 
			, captchaAnswer : { required: "보안코드를 입력해 주세요." }
		},
		showErrors: function(errorMap, errorList) {
	    	if( errorList && errorList.length > 0 ){
	    		for( var i = 0; i < errorList.length; i++ ){                			
					alert(errorList[i].message);
	               	errorList[i].element.focus();
	    			break;
	    		}
	    	}
	    },
	    ignore: [],
	    focusInvalid:false,
	    onfocusout: false,
	    onkeyup: false,
	    onclick: false
	});
}

//=== ]]검증 ========== 
</script>



<!-- 회원가입 -->
<div class="modal" id="join-modal">
	<div class="bg"></div>
	<div class="modal-inner modal-border">
		<h1>회원가입</h1>
		<form name="regForm" action="" method="post" >
			<fieldset>
				<legend>가입</legend>
				<input type="text" name="email" placeholder="이메일">
				<input type="password" name="passwd" id="passwd" placeholder="비밀번호">
				<input type="password" name="passwdCheck" placeholder="비밀번호 확인">

				<div class="checkbox">
					<input type="checkbox" name="agree" id="agree" value=""> 
					<label for="agree"><a>이용약관</a>과 <a>개인정보수집 및 이용</a>에 동의합니다</label>
				</div>

				<!-- <a href="#join2-modal" class="btn btn-modal">가입하기</a> --> 
				<a href="javascript:regFormSubmit();"  class="btn btn-modal">가입하기</a>
				<!-- button type="button" class="btn-facebook"><img src="/resources/image/common/blt_facebook.png"> facebook 으로 가입하기</button-->
			</fieldset>
		</form>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>
<script>
/** 회원가입 */
var flag_regFormSubmit = false; //flag
function regFormSubmit(isFB, fbUser) {
	//modalShow('#join2-modal'); //test
	//return;
	
	//
	var myForm = $('form[name="regForm"]');
	if( !isFB ) {
		if(!myForm.valid()) {
			return;
		}
	}
	
	if(flag_regFormSubmit) {
		return;
	}
	flag_regFormSubmit = true;
	
	var param = myForm.serialize();
	if( isFB ) {
		param = {'email':fbUser.email, 'fbAccessToken':fbUser.token};
	}
	
	console.log("register user param=" + param);
	
	//== 
	$.ajax({
        url: '/register.ajax',
        type: 'post',
        data: param,
        complete : function(_data){
			flag_regFormSubmit = false;
		},
        error : function(_data) {
        	console.log(_data);
			alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	console.log(_data);
        	if(_data.result == '1') {
        		modalShow('#join2-modal');
        	} else if(_data.result == ErrCode.V_EMAIL_DUP) { //이메일 중복
        		alert('이메일 중복입니다.'); 
        	} else {
        		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        	}
        }
    });
	
}
//=== 검증 ==========
$(function(){
	regFormValidRuleInit();
});

function regFormValidRuleInit(){
	//===암호검증 rule
	addRulePasswdValidate(); 
	//===
	//== 
	var myForm = $('form[name="regForm"]');
	myForm.validate({
		rules:{
			agree : { required: true } 
			, email : { required: true, email: true }
			, passwd : { required: true, passwdValidate: true }
			, passwdCheck : { equalTo: "#passwd" } 
		},
		messages: {
			agree : { required: "이용약관, 개인정보수집 및 이용에 모두 동의해야 합니다." }
			, email : { required: "이메일을 입력해 주세요.", email: "정확한 이메일을 입력하세요." } 
			, passwd : { required: "비밀번호를 입력해 주세요." }
			, passwdCheck : { equalTo: "비밀번호 확인을 다시 해 주십시오." }
		},
		showErrors: function(errorMap, errorList) {
	    	if( errorList && errorList.length > 0 ){
	    		for( var i = 0; i < errorList.length; i++ ){                			
					alert(errorList[i].message);
	               	errorList[i].element.focus();
	               	// 특수처리:
	               	if(errorList[i].element.name == 'passwd') {
	               		myForm.find('[name="passwd"]').val('');
	               		myForm.find('[name="passwdCheck"]').val('');
	               	} else if(errorList[i].element.name == 'passwdCheck') {
	               		myForm.find('[name="passwdCheck"]').val('');
	               	} 
	    			break;
	    		}
	    	}
	    },
	    ignore: [],
	    focusInvalid:false,
	    onfocusout: false,
	    onkeyup: false,
	    onclick: false
	});
}

/*
* 암호검증 rule
*/
function addRulePasswdValidate() {
	
	//===암호규칙 ===
	//암호는 대소문자를 구분합니다
	//6자 이상 15자 이하이어야 합니다.
	//최소 하나의 영문자(a-z; A-Z)를 포함해야 합니다.
	//최소 하나의 숫자(0-9)를 포함해야 합니다.
	//최소 하나의 특수 문자를 포함해야 합니다. 예를 들면 다음과 같습니다. (  _+!@#  ) 
	//===암호규칙 ===
		
	var customRule =  function(value, element, params) {
		var passwd = value;
		
		//==패스월드는 6자 이상 15자 이하의 영문자,숫자,특수문자(_+!@#)로만 구성될수 있습니다.
		var regPw = /^[a-zA-Z0-9_+!@#]{6,15}$/;
		if(!regPw.test(passwd)) {
			return false;
		}
		
		//==최소 하나의 영문자/숫자/특수문자가 포함되여야 함니다.
		var existAlp = false; //영어
		var existNum = false; //숫자
		var existSpe = false; //특수문자
		for(var i=0; i<passwd.length; i++) {
			var ch = passwd[i];
			if(/[a-zA-Z]/.test(ch)) {
				existAlp = true;
				continue;			
			}
			if(/[0-9]/.test(ch)) {
				existNum = true;
				continue;
			}
			if(/[_+!@#]/.test(ch)) {
				existSpe = true;
				continue;
			}
		}
		if(!existAlp) {
			return false;
		}
		if(!existNum) {
			return false;
		}
		if(!existSpe) {
			return false;
		}
		
		return true;
  	};
  
	// === rule: 	
	$.validator.addMethod("passwdValidate", 
			function(value, element, params) {
				return customRule(value, element, params);
		    },
		    "패스월드는 6자 이상 15자 이하의 영문자,숫자,특수문자(_+!@#)로만 구성될수 있습니다."
	);
}
//=== ]]검증 ========== 
</script>


<!-- 회원가입:상세 -->
<jsp:include page="/openRegister2.do" />

<!-- li class="active"><a href="#">웨어러블 스마트 패션 2조</a></li -->
<script id="tmpl-groupProjectSearchRow" type="text/x-jsrender">
	<li data-project_seq="{{:seq}}" ><a onclick="toggleParent(this, 'active');" style="cursor: pointer;" >{{:projectName}}</a></li>
</script>
<!-- 포로젝트 조회 -->
<div class="modal" id="project-search">
	<div class="bg"></div>
	<div class="modal-inner">
		<h1>프로젝트 조회</h1>
		<div class="search">
			<input type="text" name="schProjectName" />
			<button type="button"><img src="/resources/image/sub/blt_searchGray.png" onclick="searchGroupProject();" alt="검색"></button>
		</div>
		<div class="search-list">
			<ul>
				<li class="active" data-project_seq="{{:seq}}" ><a onclick="toggleParent(this, 'active');" style="cursor: pointer;" >{{:projectName}}</a></li>
				<li class="active" data-project_seq="{{:seq}}" ><a onclick="toggleParent(this, 'active');" style="cursor: pointer;" >{{:projectName}}</a></li>
				<li class="active" data-project_seq="{{:seq}}" ><a onclick="toggleParent(this, 'active');" style="cursor: pointer;" >{{:projectName}}</a></li>
				<li class="active" data-project_seq="{{:seq}}" ><a onclick="toggleParent(this, 'active');" style="cursor: pointer;" >{{:projectName}}</a></li>
				<li class="active" data-project_seq="{{:seq}}" ><a onclick="toggleParent(this, 'active');" style="cursor: pointer;" >{{:projectName}}</a></li>
				<li class="active" data-project_seq="{{:seq}}" ><a onclick="toggleParent(this, 'active');" style="cursor: pointer;" >{{:projectName}}</a></li>
			</ul>
		</div>
		<a class="btn-complete" style="cursor: pointer;" >완료</a>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>
<script type="text/javascript">
/**
 * 프로젝트 조회 초기화
 */
var projectSearchList = null;
var tmplGroupProjectSearchRow = null;
$(function(){
	projectSearchList = $('#project-search').find('.search-list');
	tmplGroupProjectSearchRow = $('#tmpl-groupProjectSearchRow').html();
});
/**
 * 프로젝트 그룹에 프로젝트 추가
 * 프로젝트 조회 완료 버튼 클릭 시 호출 된다.
 * modalShow에서 완료 버튼 이벤트를 바인딩 한다.
 */
function addGroupProject(opts){
	var groupSeq = opts.groupSeq || null;
	if( groupSeq == null ){
		alert('프로젝트 그룹이 선택되지 않았습니다.\n 다시 시도해 주세요.');
		return;
	}
	
	var projectSeqs = new Array(); 
	var activeLis = projectSearchList.find('ul li.active');
	activeLis.each(function(){
		var seq = $(this).data('project_seq');
		projectSeqs.push(seq);
		
	});
	
	if( projectSeqs.length <= 0 ){
		modalHide();
		return;
	}
	
	$.ajax({
        url: '/project/addGroupProject.ajax',
        type: 'post',
        cache: false,
        data: {projectGroupSeq: groupSeq, projectSeq: projectSeqs},
        complete : function(_data){
        	
		},
        error : function(_data) {
        	console.log(_data);
			alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	var result = _data.result;
        	if( '100' == result ){
        		if( confirm('로그인이 필요합니다. 로그인 하시겠습니까?') ){
        			modalShow('#login-modal');
        			
        		}       		
        		return;
        	}
        	
        	if( '200' == result ){
        		alert('프로젝트 추가가 실패 하였습니다.\n 다시 시도해 주세요.');       		
        		return;
        	}
        	
        	modalHide();
        	if( opts.onComplete ){
        		opts.onComplete(groupSeq);	
        	}
        }
    });
}
/**
 * 프로젝트 검색
 * modalShow에서 이벤트를 바인딩 한다.
 */
function searchGroupProject(opts){
	
	var schPName = $('#project-search').find('input[name="schProjectName"]');
	var val = schPName.val(); 	
	
	$.ajax({
        url: '/project/searchGroupProject.ajax',
        type: 'post',
        data: { schProjectName: val},
        complete : function(_data){
        	
		},
        error : function(_data) {
        	console.log(_data);
			alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	var result = _data.result;
        	if( '100' == result ){
        		if( confirm('로그인이 필요합니다. 로그인 하시겠습니까?') ){
        			modalShow('#login-modal');
        			
        		}       		
        		return;
        	}
        	
        	var normalLis = projectSearchList.find('ul li').not('.active');
        	normalLis.remove();
        	
        	var list = _data.list;
        	if( list && list.length > 0 ){
        		var activeLis = projectSearchList.find('ul li.active');
            	activeLis.each(function(){
            		var projectSeq = $(this).data('project_seq');             		
					for( var i = 0; i < list.length; i++ ){
						var aData = list[i];
						if( projectSeq == aData.seq ){
							list.remove(i);
							break;
						}
					}
            	});	
            	
            	if( list.length > 0 ){
            		var rows = new Array(); 
            		for( var i = 0; i < list.length; i++ ){
						var formattedHtml = $.templates(tmplGroupProjectSearchRow).render( list[i] );
						rows.push( $( formattedHtml ) );
					}
            		projectSearchList.find('ul').append(rows);
            	}
        	}
        }
    });
}

function toggleParent(thisObj, className){
	$(thisObj).parent().toggleClass(className);
}

function requestCaptCha() {
	$('#captChaImage').attr('src','/requestCaptCha.do');
}

var flag_findPassword = false;
function findPassword() {
	
	var myForm = $('form[name="captchaForm"]');
	if(!myForm.valid()) {
		return;
	}
	
	if(flag_findPassword) {
		return;
	}
	flag_findPassword = true;
	
	var param = {'mail.target':$("#captchaEmail").val(), 
			captchaAnswer:$("#captchaAnswer").val(), 
			'mail.title':'[OpenDesign 비밀번호 찾기]', 
			'mail.template':'mail_password.vm'
	};
	
	$.ajax({
        url: '/findPassword.ajax',
        type: 'post',
        data: param,
        complete : function(_data){
			flag_findPassword = false;
		},
        error : function(_data) {
        	console.log(_data);
			alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
        },
        success : function(_data){
        	console.log(_data);
        	if(_data.result == '-1') {
        		alert("보안코드 인증에 실패 하셨습니다. 확인 후 다시 요청해 주세요."); 
        	} else if(_data.result == '1') {
        		alert("입력하신 메일로 비밀번호가 발송 되었습니다. \n확인 후 로그인해 주세요.");
        		$('#captchaAnswer').val('');
        		$('#captchaEmail').val('');
        		
        		$('form[name="loginForm"] input[name="email"]').val('')
        		$('form[name="loginForm"] input[name="passwd"]').val('')
        		
        		modalShow('#login-modal');
        	} else if(_data.result == '-2'){
        		alert("입력하신 이메일에 대한 사용자 정보가 없습니다.\n다시 확인 후 요청해 주세요.");
        	}
        }
    }); 
}

</script>


<!-- 비밀번호 찾기 -->
<div class="modal" id="pw-modal">
	<div class="bg"></div>
	<div class="modal-inner modal-border">
		<h1>비밀번호 찾기</h1>
		<form name="captchaForm" id="captchaForm">
			<fieldset>
				<legend>비밀번호 찾기</legend>
				<div class="form-row">
					<label for="" class="km-required">이메일</label>
					<input type="text" name="captchaEmail" id="captchaEmail">
				</div>
				<div class="form-row">
					<label for="" class="km-required">보안코드</label>
					<input type="text" name="captchaAnswer" id="captchaAnswer"/>
				</div>
				
				<div class="code">
					<img alt="보안코드 이미지" id="captChaImage" src="" />
				</div>
				<button type="button" onclick="javascript:findPassword();" class="btn-confirm btn-red">확인</button>
			</fieldset>
		</form>
		<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
	</div>
</div>

<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '<%=PropertyUtil.getProperty("fb.appId")%>',
      xfbml      : true,
      version    : 'v2.10'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));

</script>
