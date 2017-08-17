<%-- 화면ID : OD04-02-03 --%>
<%@page import="com.opendesign.utils.CmnConst.MemberDiv"%>
<%@page import="com.opendesign.vo.DesignWorkFileVO"%>
<%@page import="com.opendesign.vo.DesignPreviewImageVO"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="com.opendesign.vo.DesignerVO"%>
<%@page import="com.opendesign.vo.DesignWorkVO"%>
<%@page import="com.opendesign.utils.CmnConst.RstConst"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String schMemberDiv = (String)request.getAttribute("schMemberDiv");  //회원구분
	//디자인
	DesignWorkVO itemVO = (DesignWorkVO)request.getAttribute(RstConst.P_NAME);
	//디자이너 
	DesignerVO designerVO = (DesignerVO)request.getAttribute("designerVO");
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!-- ***** 포인트 현황 초기화 ***********-->
<script id="tmpl-pointTemplete" type="text/x-jsrender">
					<div id="pointInfo" class="my-condition">
						<h3>{{:userName}}님 포인트 현황</h3>
						<p>사용 가능 {{:availDisplayPoint}}pt</p>
						<div>
							<input type="text" value="<%=itemVO.getDisplayPoint()%>pt" class="price" readonly>
							<button  type="button">포인트 사용</button>
							<input type="text" value="구매 후 잔여 포인트 {{:leftOverDisplayPoint}}pt" class="no-border" readonly>
						</div>
					</div>
</script>
<script>
$(function(){
	selectPointInfo();
});
/**
 * 포인트 현화 조회
 */
function selectPointInfo(){
		$.ajax({
	        url: '/designer/selectPointInfo.ajax',
	        type: 'post',
	        data: { seq : '<%=itemVO.getSeq()%>' },
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	console.log(_data);
	        	var htmlJ = $($.templates('#tmpl-pointTemplete').render(_data));
	        	$('#pointInfo').replaceWith(htmlJ);
	        	refreshBuyProductEvents(_data.canBuy);
	        }
	    });
}
</script>
<!-- ***** ]]포인트 현황 초기화 ***********-->
<script>
/**
 * 포트폴리오  view
 */
function goPortfolioView(seq) {
	<%	if(MemberDiv.DESIGNER.equals(schMemberDiv)) { %>
	window.location.href = '/designer/portfolio.do?seq=' + seq;
	<% } else if(MemberDiv.PRODUCER.equals(schMemberDiv))  { %>
	window.location.href = '/producer/portfolio.do?seq=' + seq;
	<% } else if(MemberDiv.PRODUCT.equals(schMemberDiv))  { %>
	window.location.href = '/product/portfolio.do?seq=' + seq;
	<% } %>
}

/**
 * 디자인 디자인상세
 */
function goProductView(seq) {
	<%	if(MemberDiv.DESIGNER.equals(schMemberDiv)) { %>
	window.location.href='/designer/productView.do?seq=' + seq;
	<% } else if(MemberDiv.PRODUCER.equals(schMemberDiv))  { %>
	window.location.href='/producer/productView.do?seq=' + seq;
	<% } else if(MemberDiv.PRODUCT.equals(schMemberDiv))  { %>
	window.location.href='/product/productView.do?seq=' + seq;
	<% } %>
}

/**
 * init event
 */
function refreshBuyProductEvents(canBuy) {
	if(canBuy) {
		$('#btnBuyProduct').prop('disabled', false);
		$('#btnBuyProduct').click(function(){
			buyProduct();
		}); 
	} else {
		$('#btnBuyProduct').off();
		$('#btnBuyProduct').prop('disabled', true);
	}
}

/**
 * 배송정보
 */
function calcDedeliveryInfo() {
	var result = '';
	var myForm = $('form[name="deliveryForm"]'); 
	var data = myForm.serializeObject();
	
	if(data.toName != '') {
		result = $.templates('받는 사람: {{:toName}} | 전화 번호: {{:toPhone}} | 주소: {{:toAddress1}} {{:toAddress2}}').render(data);
		$('form[name="buyForm"]').find('[name="deliveryInfo"]').val(result);
	} 
	console.log('>>> calcDedeliveryInfo');
	console.log(result);
	return true;
}


/**
 * 포인트 사용(결제)
 */
var flag_buyProduct = false; //flag
function buyProduct(){
	checkedLogin(function(){
		console.log('>>> buyProduct');
		
		//배송정보
		if(!calcDedeliveryInfo()) {
			return;
		}
		
		var myForm = $('form[name="buyForm"]');
		if(flag_buyProduct) {
			return;
		}
		flag_buyProduct = true;
		
		$.ajax({
	        url: '/designer/buyProduct.ajax',
	        type: 'post',
	        data: myForm.serialize(),
	        complete : function(_data){
	        	flag_buyProduct = false;
			},
	        error : function(_data) {
	        	console.log(_data);
				alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        },
	        success : function(_data){
	        	if( _data.result == '1' ) {
	        		// 성공후 disable
	        		$('#btnBuyProduct').off();
	        		$('#btnBuyProduct').prop('disabled', true);
	        		alert('디자인 구매 성공하셨습니다.');
	        		goProductView('<%=itemVO.getSeq()%>');
	        	} else if( _data.result == '600' ) {
	        		alert("이미 구매하셨습니다..");
	        	} else if( _data.result == '300' ) {
	        		alert("포인트가 부족합니다.");
	        	} else {
	        		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
	        	}
	        }
	    });
	}); //end of checkedLogin
}

</script>

</head>
<body>

<!-- 구매 form -->
<form name="buyForm">
	<input type="hidden" name="designWorkSeq" value="<%=itemVO.getSeq()%>" /> 
	<input type="hidden" name="deliveryInfo" value="" />
</form>

<div class="wrap">
	<!-- header -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"> 
		<jsp:param name="headerCategoryYN" value="N" />
	</jsp:include>
	<!-- //header -->

	<!-- content -->
	<div class="detail-content">
		<div class="inner">
			<div class="purchase-wrap">
				<div class="point-purchase" style="border-top: none;">
					<div id="pointInfo" class="my-condition">
						<h3> 님 포인트 현황</h3>
						<p>사용 가능  pt</p>
						<div>
							<input type="text" value="<%=itemVO.getDisplayPoint()%>pt" class="price" readonly>
							<button  type="button">포인트 사용</button>
							<input type="text" value="구매 후 잔여 포인트 pt" class="no-border" readonly>
						</div>
					</div>

					<div class="delivery">
						<h3>배송지 선택 <span>(배송 받으실 물건이 있으시다면 입력해 주세요)</span></h3>
						<form name="deliveryForm">
							<table>
								<tr>
									<th scope="row">받는 사람</th>
									<td><input type="text" name="toName" placeholder="받는 사람" /></td>
								</tr>
								<tr>
									<th scope="row">전화 번호</th>
									<td><input type="text" name="toPhone" placeholder="전화 번호" /></td>
								</tr>
								<tr>
									<th scope="row">주소</th>
									<td>
										<!-- <button type="button">주소찾기</button>
										<input type="text" class="zipcode" name="toZip" /> -->
										<input type="text" class="address" name="toAddress1" placeholder="주소" />
										<input type="text" class="address" name="toAddress2" placeholder="상세주소" />
									</td>
								</tr>
							</table>
							<p>* 구입한 디자인은 마이페이지에서 확인할 수 있습니다.</p>
							<button id="btnBuyProduct" type="button"  class="btn-complete" disabled="disabled">포인트 결제완료</button>
						</form>
					</div>
				</div>
				<h2 class="title" style="margin-top: 200px;"><%=itemVO.getTitle()%></h2>
				<div class="img-area">
					<p class="cate"><%=itemVO.getCateNames()%></p>
					<img src="<%=itemVO.getThumbUri()%>"  alt="대표 이미지">
				</div>
				<div class="info-section">
					<div class="designer-area">
						<div class="profile-pic"><img src="<%=designerVO.getImageUrl()%>" alt=""></div>
						<a href="javascript:goPortfolioView('<%=designerVO.getSeq()%>');" class="name"><%=designerVO.getUname()%></a>
						<a href="javascript:goShowMsgView('<%=designerVO.getSeq()%>');" class="btn-msg"><img src="/resources/image/sub/btn_msg.png" alt="메세지 보내기"></a>
					</div>
					<div class="info">
						<dl class="licenses">
							<dt>라이센스</dt>
							<dd>
								<% if("1".equals(itemVO.getLicenseBY())) { %><span><img src="/resources/image/sub/licenses_by.png" alt="BY"></span><% } %>
								<% if("1".equals(itemVO.getLicenseNC())) { %><span><img src="/resources/image/sub/licenses_nc.png" alt="NC"></span><% } %>
								<% if("1".equals(itemVO.getLicenseND())) { %><span><img src="/resources/image/sub/licenses_nd.png" alt="ND"></span><% } %>
							</dd>
						</dl>
						<dl class="date">
							<dt>게시일</dt>
							<dd><%=itemVO.getDisplayRegTime()%></dd>
						</dl>
						<dl class="like">
							<dt>좋아요</dt>
							<dd><%=itemVO.getLikeCnt()%></dd>
						</dl>
						<dl class="point">
							<dt>포인트</dt>
							<dd><%=itemVO.getDisplayPoint()%>pt</dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- //content -->

	<!-- footer -->
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	<!-- //footer -->
</div>
<!-- modal -->
<%@include file="/WEB-INF/views/common/modal.jsp"%>
<!-- //modal -->
</body>
</html>