<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.opendesign.vo.MessageVO.MessageMode"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String searchWord = StringUtils.stripToEmpty(request.getParameter("searchWord"));
%>
<style>

.totalsearch {
	height:42px;
}

#searchWord {
	height: 42px;
	text-indent: 5px;
	font-style: italic;
	font-size: 14px;
}

.searchBtnWrap{
	width: 42px;
	height: 42px;
}

#searchBtn {
	line-height: 42px;
	color: #f00;
	
}

</style>
	<div class="inner">
		<h1>
			<a href="/main.do"><img src="/resources/image/common/logo.jpg" alt="open src Design"></a>
		</h1>

		<div class="util-area">
			<!-- 통합검색 -->
			<h2 class="skip">통합검색</h2>
			<div class="totalsearch">
				<form name="inteSearchForm" id="inteSearchForm" action="/search/search.do">
					<fieldset>
						<legend>통합검색</legend>
						<input type="text" name="searchWord" id="searchWord" title="검색어 입력" onkeyup="inteSearch(true);" value="<%=searchWord%>" placeholder="search" />
						<button class="searchBtnWrap" type="submit" onclick="return false;"><i id="searchBtn" class="fa fa-search" aria-hidden="true" onclick="inteSearch();" alt="검색"></i></button>
					</fieldset>
				</form>
			</div>
			<!-- //통합검색 -->
			
			<h2 class="skip">개인화 영역</h2>
			<!-- 로그인 했을때만 보임 -->
			<%	if(CmnUtil.isUserLogin(request)) {	%> 
			<div class="noti">
				<!-- 메세지가 있을 시 클래스 active 추가-->
				<div id="msgDiv" class="message ">
					<a href="javascript:void(0);" onclick="showMsgDiv();" class="btn-modal">
						<i class="fa fa-comment" aria-hidden="true"></i>
					</a>
					<span id="latestMsgNumber" class="number"></span>
				</div>
				<!-- 알림이 있을 시 클래스 active 추가-->
				<div id="alarmDiv" class="alert ">
					<a href="javascript:void(0);" onclick="showAlarmDiv();" class="btn-modal">
						<i class="fa fa-bell" aria-hidden="true"></i> 
					</a>
						<span id="latestAlarmNumber" class="number">2</span>
				</div>
				<!-- 마이페이지 -->
				<div id="mypageDiv" class="mypage">
						<a href="/user/myPage.do" class="btn-mypage">
							<i class="fa fa-user" aria-hidden="true"></i> 
						</a>
				</div>
			</div>
			<% 	} %>
			<!-- //로그인 했을때만 보임 -->
			
			<%	if(CmnUtil.isUserLogin(request)) {	%>
			<a href="/logout.do" class="btn-logout btn-red">로그아웃</a>
			<% 	} else { %>
			<a href="javascript:modalShow('#login-modal');" class="btn-login btn-modal btn-red">로그인/회원가입</a>
			<% 	} %>
			
			<%	if(CmnUtil.isUserLogin(request)) {	%> 
			<!-- <a href="/user/myPage.do" class="btn-mypage">마이페이지</a>  -->
			<% 	} %>
		</div>
		
<!-- **************************  메시지  ************************* -->
		<div class="modal noti-modal" id="message" style="display:none;">
			<div class="bg"></div>
			<div class="noti-inner">
				<div class="msg-search">
					<form name="msgSearchForm" onsubmit="msgSearchBtnClick();return false;">
						<input type="hidden" name="schMode" /> 
						<input type="hidden" name="schNewUserSeq" /> 
						<fieldset>
							<legend class="msg-head">메시지 검색</legend>
							<input type="text" name="schWord" placeholder="검색" />
							<button onclick="msgSearchBtnClick();" class="btn-red" type="button">
								<i class="fa fa-search" aria-hidden="true"></i>
							</button>
						</fieldset>
					</form>
				</div>
				<div class="msg-latest">
				<legend class="latest msg-head" id="latestMsgNumber2">최근 받은 메시지</legend>
				<!-- 메시지가 없는 경우
				<p class="no-msg">편지함에 메시지가 없습니다.</p>
				-->
				<ul id="msgRoomList" class="msg-list">
					<%-- 
					<li class="msg">
						<div>
							<div class="pic"><img src="/resources/image/common/pic_profile.jpg" alt="송준기"></div>
							<dl>
								<dt>송준기</dt>
								<dd>안녕하세요 작가님, 작품때문에 문의가 있어서 메시지 드립니다. 편하실 때 통화 가능하실까요?</dd>
							</dl>
							<span class="date">오후 11:34분</span>
						</div>
					</li>
					 --%>
				</ul>
				</div>
				<div class="talking-list">
					<ul id="msgContentList" class="msg-list culunm">
						<%--
						<li class="msg">
							<div>
								<div class="pic"><img src="/resources/image/common/pic_profile.jpg" alt="송준기"></div>
								<dl>
									<dt>송준기</dt>
									<dd>안녕하세요 작가님, 작품때문에 문의가 있어서 메시지 드립니다. 편하실 때 통화 가능하실까요?</dd>
								</dl>
								<span class="date">오후 11:34분</span>
							</div>
						</li>
						<li class="me">
							<div>
								<div class="pic"><img src="/resources/image/common/pic_profile.jpg" alt="송준기"></div>
								<dl>
									<dt>나</dt>
									<dd>알겠습니다.<br>그럼 다시 연락드리도록 하겠습니다</dd>
								</dl>
								<span class="date">오후 11:34분</span>
							</div>
						</li>
						 --%>
					</ul>
					<div class="msg-edit">
						<form name="msgAddForm">
							<input type="hidden" name="recieveSeq" value="" />
							<fieldset>
								<legend class="msg-head">메시지 작성</legend>
								<textarea name="contents" maxlength="200" placeholder="메시지를 작성해주세요"></textarea>
								<button class="btn-red" onclick="msgAddFormInsertMsg();" type="button">메시지보내기</button>
							</fieldset>
						</form>
					</div>
				</div>
				<button type="button" class="btn-close"><i class="fa fa-times fa-2x" aria-hidden="true"></i></button>
			</div>
		</div>
<!-- message -->
<script id="tmpl-msgRoomListTemplete" type="text/x-jsrender">
					<li onclick="msgRoomClick(this);" data-new="{{:isNew}}" data-seq="{{:roomUserSeq}}" class="msg">
						<div>
							<div class="pic"><img style="width:68px;height:68px;" src="{{:roomUserImageUrl}}" alt="{{:roomUserName}}"></div>
							<dl>
								<dt>{{:roomUserName}}</dt>
								<dd class="ellip1">{{:contents}}</dd>
							</dl>
							<span class="date">{{:displayTime}}</span>
						</div>
					</li>
</script>
<script id="tmpl-msgContentListTemplete" type="text/x-jsrender">
						<!-- class: msg / me -->
						{{if  loginUserRecieveUser }}
						<li class="msg">
						{{else}}
						<li class="me">
						{{/if}}
							<div>
								<div class="pic"><img style="width:68px;height:68px;" src="{{:sendImageUrl}}" alt="{{:sendName}}"></div>
								<dl>
									{{if  loginUserRecieveUser }}
									<dt>{{:sendName}}</dt>
									{{else}}
									<dt>나</dt>
									{{/if}}
									<dd>{{:contents}}</dd>
								</dl>
								<span class="date">{{:displayTime}}</span>
							</div>
						</li>
</script>

<form name="msgContentForm">
	<input type="hidden" name="schSelectedUserSeq" /> 
</form>

<script>
/**
 * 최신 메시지 신호 받았을때
 */
$(function(){
	onNotifyMsgChanged(); 
});

/**
 * 최신 메시지 신호 받았을때
 */
function onNotifyMsgChanged() {
	console.log('>>> onNotifyMsgChanged');
	// === 1. messageDiv refresh
	refreshMsgDiv();
	
	// === 2. message room list refresh
	refreshMsgRoomList();
	
	// === 3. message content list refresh
	refreshMsgContentList();
}

function refreshMsgDiv() {
	console.log('>>> refreshMsgDiv');
	$.ajax({
        url: '/common/selectLatestMessageCount.ajax',
        type: 'post',
        data: { },
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
			if(_data) { 
				var cnt = _data.result;
				if(!cnt) {
					cnt = 0; 
				}
				if(cnt > 0) {
					$('#msgDiv').addClass('active');
					$('#latestMsgNumber').text(cnt);
					$('#latestMsgNumber2').text( '최근 받은 메시지 ({0})'.replace('{0}',cnt) );
				} else {
					$('#msgDiv').removeClass('active');
					$('#latestMsgNumber').text('');
					$('#latestMsgNumber2').text( '최근 받은 메시지 ({0})'.replace('{0}',cnt) );
				}
			}
		}
    });
}

/**
 * 메시지 room
 */
function refreshMsgRoomList() {
	
	var myForm = $('form[name="msgSearchForm"]');
	
	console.log('>>> refreshMsgRoomList');
	console.log(myForm.serialize());
	
	if(!$('#message').is(':visible')) {
		console.log('message div is hidden.');
		return;
	}
	
	$.ajax({
        url: '/common/selectLatestMessageRoomList.ajax',
        type: 'post',
        data: myForm.serialize(),
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
			if(_data) {
				var list = _data.result;
				$('#msgRoomList').empty();
				if(!list) {
					console.log('>>> refreshMsgRoomList no data.');
					return;
				}
				//
				var htmlJ = $($.templates('#tmpl-msgRoomListTemplete').render(list));
				$('#msgRoomList').append(htmlJ);
				
				// 선택된 사용자:  
			}
		}
    });
}

/**
 * 메시지 content
 */
function refreshMsgContentList() {
	var myForm = $('form[name="msgContentForm"]');
	
	console.log('>>> refreshMsgContentList');
	console.log(myForm.serialize());
	
	if(!$('#message').is(':visible')) {
		console.log('message div is hidden.');
		return;
	}
	
	$.ajax({
        url: '/common/selectLatestMessageContentList.ajax', 
        type: 'post',
        data: myForm.serialize(),
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
			// 상태 읽음으로 될수 있음으로 개수 refresh한다.
			refreshMsgDiv();
			
			if(_data) {
				var list = _data.result;
				$('#msgContentList').empty();
				if(!list) {
					console.log('>>> refreshMsgContentList no data.');
					return;
				}
				//
				var htmlJ = $($.templates('#tmpl-msgContentListTemplete').render(list));
				$('#msgContentList').append(htmlJ);
				
				// scroll to bottom of div:
				$('#msgContentList').scrollTop($('#msgContentList').prop("scrollHeight"));
				
			}
		}
    });
}


/**
 * 메시지창 보여주기 : 검색모드
 */
function showMsgDiv() {
	console.log('>>> showMsgDiv');
	var searchForm = $('form[name="msgSearchForm"]');
	searchForm.find('[name="schMode"]').val('<%=MessageMode.SEARCH%>');
	searchForm.find('[name="schWord"]').val('');
	searchForm.find('[name="schNewUserSeq"]').val('');
	//
	var contentForm = $('form[name="msgContentForm"]');
	contentForm.find('[name="schSelectedUserSeq"]').val(''); 
	
	modalShow('#message');
	onNotifyMsgChanged();
}

/**
 * 메시지창 보여주기 : 새 사용자 모드
 */
function showMsgDivNewUser(newUserSeq) {
	console.log('>>> showMsgDivNewUser: newUserSeq=' + newUserSeq);
	var searchForm = $('form[name="msgSearchForm"]');
	searchForm.find('[name="schMode"]').val('<%=MessageMode.NEW%>');
	searchForm.find('[name="schWord"]').val('');
	searchForm.find('[name="schNewUserSeq"]').val(newUserSeq); //새 사용자 
	//
	var contentForm = $('form[name="msgContentForm"]');
	contentForm.find('[name="schSelectedUserSeq"]').val(''); 
	
	modalShow('#message');
	onNotifyMsgChanged();
}

/**
 * 메시지 보내기: 새 사용자 모드
 */
function goShowMsgView(seq) {
	checkedLogin(function(){
		// scroll to top
		$('body').scrollTop(0);
		showMsgDivNewUser(seq);
	}); //end of checkedLogin
}

/**
 * 메시지 검색버튼:
 */
function msgSearchBtnClick() {
	console.log('>>> msgSearchBtnClick:');
	var searchForm = $('form[name="msgSearchForm"]');
	searchForm.find('[name="schMode"]').val('<%=MessageMode.SEARCH%>');
	//searchForm.find('[name="schWord"]').val('');
	searchForm.find('[name="schNewUserSeq"]').val(''); //새 사용자 
	//
	var contentForm = $('form[name="msgContentForm"]');
	contentForm.find('[name="schSelectedUserSeq"]').val(''); 
	 
	onNotifyMsgChanged();
}

/**
 * 메시지 room click
 */
function msgRoomClick(thisObj) {
	console.log('>>> msgRoomClick');
	var roomUserSeq = $(thisObj).data('seq'); 
	var myForm = $('form[name="msgContentForm"]');
	myForm.find('[name="schSelectedUserSeq"]').val(roomUserSeq);
	
	onNotifyMsgChanged();
}

/**
 * 메시지 발송
 */
var flag_msgAddFormInsertMsg = false; //flag
function msgAddFormInsertMsg() {
	console.log('>>> msgAddFormInsertMsg');
	
	var myForm = $('form[name="msgAddForm"]');
	var contents = myForm.find('[name="contents"]');
	if(contents.val().trim() == '') {
		alert('메시지 입력하세요.');
		contents.focus();
		return;
	}
	var contentForm = $('form[name="msgContentForm"]');
	var schSelectedUserSeq = contentForm.find('[name="schSelectedUserSeq"]');
	if(schSelectedUserSeq.val() == '') {
		alert('메시지 발송대상을 선택해주세요.');
		return;
	}
	myForm.find('[name="recieveSeq"]').val(schSelectedUserSeq.val()); //받는 사람
	
	
	if(flag_msgAddFormInsertMsg) {
		return;
	}
	flag_msgAddFormInsertMsg = true;
	
	// 
	$.ajax({
        url: '/common/sendUserMessage.ajax',
        type: 'post',
        data: myForm.serialize(), 
		complete : function(_data){
			flag_msgAddFormInsertMsg = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
			if(_data.result == '1') {
				// do nothing
				contents.val(''); 
			} else {
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			}
		}
    });
}

</script>
<!-- **************************  ]]메시지  ************************* -->
		
<!-- **************************  알림  ************************* -->
		<div class="modal noti-modal" id="alert" style="display:none;">
			<div class="bg"></div>
			<div class="noti-inner">
				<p class="latest"></p>
				<ul id="alarmListView" class="alert-list">
					<%-- template
					<li>
						<div>
							<div class="pic">
								<img src="/resources/image/common/pic_profile.jpg" alt="송준기">
							</div>
							<div class="txt">송중기님이 나의 작품 “Dancing with 3D”을 13,880 포인트로
								구매하였습니다.</div>
							<span class="date">오후 11:34분</span>
						</div>
					</li>
					--%>
				</ul>
				<button onclick="alarmLoadMore();" data-name="alarmLoadMore" type="button" class="btn-more2 alarmBtn hide" >결과 더 보기</button>
			</div>
		</div>
		
<script id="tmpl-alarmListTemplete" type="text/x-jsrender">
					<li>
						<div>
							<div class="pic">
								<img style="width:68px;height:68px;"  src="{{:actorUrl}}" alt="{{:actorName}}">
							</div>
							<div class="txt">{{:wholeContents}}</div>
							<span class="date">{{:displayTime}}</span>
						</div>
					</li>
</script>
<form id="alarmListParamForm" name="alarmListParamForm" method="GET" action="" >
	<input type="hidden" name="schPage" value="1" /> 	<!-- 페이지번호 --> 
	<input type="hidden" name="schLimitCount" value="5" /> <!-- 한 page 개수 -->
</form>
<script>
/**
 * 초기화시 알림개수 조회
 */
$(function(){
	onNotifyAlarmChanged();
});

/**
 * 최신 알림개수 조회하기
 */
function onNotifyAlarmChanged() {
	console.log('>>> onNotifyAlarmChanged');
	// 
	$.ajax({
        url: '/common/selectLatestAlarmCount.ajax',
        type: 'get',
        data: { },
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log('>>> selectLatestAlarmCount');
			console.log(_data);
			if(_data && _data.result > 0) {
				var cnt = _data.result;
				$('#alarmDiv').addClass('active');
				$('#latestAlarmNumber').text(cnt);
			} else {
				$('#alarmDiv').removeClass('active');
				$('#latestAlarmNumber').text('');
			}
		}
    });
}

/**
 * 알림창 보여주기 
 */
function showAlarmDiv() {
	//active 지우고 number 지운다
	$('#alarmDiv').removeClass('active');
	$('#latestAlarmNumber').text(''); 
	// update 및 조회
	updateAndSearchAlarm();
	modalShow('#alert');
}

/**
 * 알림 update 및 조회
 */
var flag_updateAndSearchAlarm = false; //flag
function updateAndSearchAlarm() {
	
	if(flag_updateAndSearchAlarm) {
		return;
	}
	flag_updateAndSearchAlarm = true;
	
	// 
	$.ajax({
        url: '/common/updateLatestAlarmRead.ajax',
        type: 'post',
        data: {  },
		complete : function(_data){
			flag_updateAndSearchAlarm = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
			console.log('>>> loadPage');
			loadAlarmPage();
		}
    });
}

</script>
<script>
//뷰 컨트롤러 생성	
var alarmListView = null;

/**
 * 초기화
 */
$(function(){
	
	// init param
	var myForm = $('form[name="alarmListParamForm"]');
	myForm.find('[name="schPage"]').val('1');
	
	//뷰 컨트롤러 생성	
	alarmListView = new ListView({
		htmlElement : $('#alarmListView')
	});
	
	// clear
	alarmListView.clear();
});


/**
 * 페이지 load
 */
var flag_loadAlarmPage = false; //flag
function loadAlarmPage() {
	
	if(flag_loadAlarmPage) {
		return;
	}
	flag_loadAlarmPage = true;
	
	var myForm = $('form[name="alarmListParamForm"]');
	// 데이터 조회 및 load
	$.ajax({
        url: '/common/selectLatestAlarmList.ajax',
        type: 'get',
        data: myForm.serialize(),
		complete : function(_data){
			flag_loadAlarmPage = false;
		},
		error : function(_data) {
			console.log(_data);
	    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		},
		success : function(_data){
			console.log(_data);
	    	// load
	    	loadAlarmPageWithData(_data);
		}
    });
}

/**
 *  loadAlarmPageWithData
 */
function loadAlarmPageWithData(_data) {
	//alarmListView.clear();
	//if(!itemList || itemList.length == 0) {
	//	console.log('>>> loadPageWithData no data.');
	//	return;
	//}
	
	// allCount
	var allCount = _data.all_count;
	console.log('>>> allCount=' + allCount);
	// pageNo
	var myForm = $('form[name="alarmListParamForm"]');
	var pageNo = myForm.find('[name="schPage"]').val();
	
	//
	var listData = _data.list;
	var listCount = listData.length;
	var existList = listCount > 0; 
	alarmListView.putData('existList', existList);
	// loadMore button
	if((! existList) || ((alarmListView.items.length + listCount)  >= allCount) ) {
		$('#alert').find('[data-name="alarmLoadMore"]').hide(); 
	} else {
		$('#alert').find('[data-name="alarmLoadMore"]').show();
	}
	if( ! existList ){	
		console.log('>>> loadPageWithData no data.');
		return;
	}
	
	// 
	alarmListView.addAll({
		keyName: 'seq'
		,data: listData
		,htmlTemplate: $('#tmpl-alarmListTemplete').html()
	});
	
	//
	
}

/**
 * 더보기: 알림
 */
function alarmLoadMore() {
	// pageNo + 1
	var myForm = $('form[name="alarmListParamForm"]');
	var pageNo = myForm.find('[name="schPage"]').val();
	if(pageNo == '') {
		pageNo = '1';
	}
	
	var nextPage = parseInt(pageNo, 10);
	nextPage++;
	myForm.find('[name="schPage"]').val(nextPage);
	
	// load 
	loadAlarmPage();
}

/*
 * 통합검색 
 */
function inteSearch(isKeyDown) {
	
	if( isKeyDown ) {
		if( event.keyCode == 13 ) {
			submitInteSearchForm();	
		}
	} else {
		submitInteSearchForm();
	}
	
}

function submitInteSearchForm() {
	if( !isEmpty($('#searchWord').val()) ) {
		 $('#inteSearchForm').submit(); 
	} else {
		alert("검색어를 입력해 주세요.");
		$('#searchWord').focus();
	}
}

</script>		
		
<!-- **************************  ]]알림  ************************* -->
		
		
		
	</div>
	