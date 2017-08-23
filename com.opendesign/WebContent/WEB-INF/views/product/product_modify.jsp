<%-- 화면ID : OD02-01-02 --%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@page import="com.opendesign.utils.CmnConst"%>
<%@page import="com.opendesign.vo.DesignWorkFileVO"%>
<%@page import="com.opendesign.vo.DesignPreviewImageVO"%>
<%@page import="com.opendesign.utils.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.opendesign.utils.CmnConst.RstConst"%>
<%@page import="com.opendesign.vo.DesignWorkVO"%>
<%@page import="com.opendesign.vo.CategoryVO"%><%
	List<CategoryVO> cateList = (List<CategoryVO>)request.getAttribute("cateList");
	//디자인
	DesignWorkVO itemVO = (DesignWorkVO)request.getAttribute(RstConst.P_NAME);
	String selCateCode = itemVO.getSelCateCode();
	List<String> tagList = CmnConst.TagList.TAG_LIST;
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body>
<div class="wrap">
	<!-- header -->
	<jsp:include page="/WEB-INF/views/common/header.jsp"> 
		<jsp:param name="headerCategoryYN" value="N" />
	</jsp:include>
	<!-- //header -->

	<!-- content -->
	<div class="content regi-content">
		<div class="inner">
			<div class="best-head">
				<span class="mainChar">D</span>
				<span>디자인 수정</span>
			</div>
			<form name="productForm" id="productForm" enctype="multipart/form-data" onsubmit="return false;" >
				<input type="hidden" name="seq" value="<%=itemVO.getSeq() %>" />
			<table class="tbl-regi">
				<colgroup>
					<col style="width:220px">
					<col style="">
					<col style="width:220px">
					<col style="">
				</colgroup>
				<tr>
					<th scope="row" class="km-required">디자인명</th>
					<td colspan="3"><input type="text" name="title" value="<%=itemVO.getTitle() %>" maxlength="50" placeholder="최대 50자 이내 입력" class="no-border"></td>
				</tr>
				<tr>
					<th scope="row" class="km-required">카테고리</th>
					<td colspan="3" id="cateWrapProdMod">
						<div class="select-area custom-select" id="fisrtCateDiv">
							<input type="text">
							<select name="cateDepth1" id="cateDepth1">
								<option value="">전체</option>
							<%-- <%for(CategoryVO aCate  : cateList){%>
								<option value="<%=aCate.getCategoryCode() %>"><%=aCate.getCategoryName() %></option>
							<%} %> --%>
							</select>
						</div>
						<div class="select-area custom-select">
							<input type="text">
							<select name="cateDepth2">
								<option value="">전체</option>
							</select>
						</div>
						<button  type="button" class="btn-add"><img src="../resources/image/common/btn_addBig.png" alt="더하기"></button>
						<ul class="cate-list">
						<%
						List<CategoryVO> productCateList = itemVO.getCateList();
						for(CategoryVO aCate : productCateList){
						%>
							<li><%=aCate.getCategoryName() %> <input type="hidden" name="categoryCodes" value="<%=aCate.getCategoryCode() %>" /></li>
						<%
						}
						%>
						</ul>
					</td>
				</tr>
				<tr>
					<th scope="row">라이센스
						<!-- 설명 -->
						<a href="javascript:void(0);" class="btn-guide"><img src="../resources/image/common/blt_guide.gif" alt="도움말"></a>
						<div class="modal-guide">
							<table class="tbl-guide">
								<caption>라이선스 안내</caption>
								<colgroup>
									<col style="width:120px">
									<col>
									<col style="width:120px">
								</colgroup>
								<thead>
									<tr>
										<th scope="col">라이선스</th>
										<th scope="col">이용조건</th>
										<th scope="col">문자표기</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="img"><img src="../resources/image/sub/by.jpg" alt="CC BY"></td>
										<td>
											<dl>
												<dt>저작자표시</dt>
												<dd>저작자의 이름, 저작물의 제목, 출처 등 저작자에 관한 표시를 <br>해주어야 합니다.</dd>
											</dl>
										</td>
										<td>CC BY</td>
									</tr>
									<tr>
										<td class="img"><img src="../resources/image/sub/bync.jpg" alt="CC BY-NC"></td>
										<td>
											<dl>
												<dt>저작자표시-비영리</dt>
												<dd>저작자를 밝히면 자유로운 이용이 가능하지만 영리목적으로 <br>이용할 수 없습니다.</dd>
											</dl>
										</td>
										<td>CC BY-NC</td>
									</tr>
									<tr>
										<td class="img"><img src="../resources/image/sub/bynd.jpg" alt="CC BY-ND"></td>
										<td>
											<dl>
												<dt>저작자표시-변경금지</dt>
												<dd>저작자를 밝히면 자유로운 이용이 가능하지만, <br>변경없이 그대로 이용해야 합니다.</dd>
											</dl>
										</td>
										<td>CC BY-ND</td>
									</tr>
									<tr>
										<td class="img"><img src="../resources/image/sub/bysa.jpg" alt="CC BY-SA"></td>
										<td>
											<dl>
												<dt>저작자표시-동일조건변경허락</dt>
												<dd>저작자를 밝히면 자유로운 이용이 가능하고 저작물의 변경도 가능하지만, <br>
												2차적 저작물에는 원 저작물에 적용된 것과 동일한 라이선스를 적용해야 <br>
												합니다.</dd>
											</dl>
										</td>
										<td>CC BY-SA</td>
									</tr>
									<tr>
										<td class="img"><img src="../resources/image/sub/byncsa.jpg" alt="CC BY-NC-SA"></td>
										<td>
											<dl>
												<dt>저작자표시-비영리-동일조건변경허락</dt>
												<dd>저작자를 밝히면 이용이 가능하며 저작물의 변경도 가능하지만, <br>
												영리목적으로 이용할 수 없고 2차적 저작물에는 원 저작물과 <br>
												동일한 라이선스를 적용해야 합니다.</dd>
											</dl>
										</td>
										<td>CC BY-NC-SA</td>
									</tr>
									<tr>
										<td class="img"><img src="../resources/image/sub/by.jpg" alt="CC BY-NC-ND"></td>
										<td>
											<dl>
												<dt>저작자표시-비영리-변경금지</dt>
												<dd>저작자를 밝히면 자유로운 이용이 가능하지만, <br>
												영리목적으로 이용할 수 없고 변경 없이 그대로 이용해야 합니다.</dd>
											</dl>
										</td>
										<td>CC BY-NC-ND</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- //설명 -->
					</th>
					<td colspan="3" class="td-license">
						<ul class="licenses-check">
							<li>
								<span class="sbj">원저작자 미표시</span>
								<div class="checkbox">
									<input type="checkbox" name="license01" id="license01_1" value="1" >
									<label for="license01_1">허락</label>
								</div>
								<div class="checkbox">
									<input type="checkbox" name="license01" id="license01_2" value="0" >
									<label for="license01_2">허락하지 않음</label>
								</div>
							</li>
							<li>
								<span class="sbj">상업적 이용</span>
								<div class="checkbox">
									<input type="checkbox" name="license02" id="license02_1" value="1" >
									<label for="license02_1">허락</label>
								</div>
								<div class="checkbox">
									<input type="checkbox" name="license02" id="license02_2" value="0" >
									<label for="license02_2">허락하지 않음</label>
								</div>
							</li>
							<li>
								<span class="sbj">디자인 변경</span>
								<div class="checkbox">
									<input type="checkbox" name="license03" id="license03_1" value="1" >
									<label for="license03_1">허락</label>
								</div>
								<div class="checkbox">
									<input type="checkbox" name="license03" id="license03_2" value="0" >
									<label for="license03_2">허락하지 않음</label>
								</div>
								<!-- 2017.07.28
								<div class="checkbox">
									<input type="checkbox" name="license03" id="license03_3" value="2" >
									<label for="license03_3">동일 라이센스 적용했을 경우 허락</label>
								</div> -->
							</li>
						</ul>
					</td>
				</tr>
				<tr class="no-padding">
					<th scope="row"></th>
					<%if(!CmnUtil.isEmpty(tagList)) { for(int i = 0; i < tagList.size(); i++) {%>
					<td style="display: inline-block; margin-right: 25px;"><button type="button" onclick="javascript:tagButtonClick(this.value);" style="background: #b5b5b5; padding: 3px 6px 2px 6px; border-radius: 9px;" name="<%= tagList.get(i)%>" value="<%= tagList.get(i)%>"><%= tagList.get(i)%></button></td>
					<%} }%>
				</tr>
				<tr>
					<th scope="row" style="padding: 12px 0 50px 30px;">태그</th>
					<%
					String[] tarArray = itemVO.getTagsArray();
					String tag = "";
					for(int i = 0; i < tarArray.length; i++){
						String aTag = tarArray[i];
						if( i > 0 ){
							tag += ", ";
						}
						tag += aTag;
					}
					%>
					<td colspan="3" style="padding: 0 0 50px 0;"><input type="text" name="tag" value="<%=tag %>" placeholder="태그와 태그는 쉼표로 구분하며, 개당 20byte 이내, 최대 5개까지 입력하실 수 있습니다." class="no-border"></td>
				</tr>
				<%-- 2017.07.28 
				<tr>
					<th scope="row" class="km-required">포인트</th>
					<td colspan="3"><input type="number" name="point" value="<%=itemVO.getPoint() != null ? itemVO.getPoint() : "0" %>" placeholder="포인트 입력" class="no-border"></td>
				</tr> --%>
				<tr>
					<th scope="row">썸네일 등록</th>
					<td colspan="3">
						<div class="file-url">
							<input type="text"  placeholder="jpg,png만 등록 가능합니다.">
							<!--button type="btn-del">x</button-->
						</div>
						<div class="file">
							<input type="file" accept="image/x-png, image/jpeg"  name="fileUrlFile">
							<button type="button">검색</button>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">디자인 등록
						<!-- 설명 -->
						<a href="javascript:void(0);" class="btn-guide"><img src="../resources/image/common/blt_guide.gif" alt="도움말"></a>
						<div class="modal-guide">
							<p class="txt-guide">작품 제작에 사용하거나<br>
							참고한 오픈소스 컨텐츠를<br>
							공유할 수 있도록 등록해주세요!</p>
						</div>
						<!-- //설명 -->
						<p class="p-guide">(등록한 순서대로 업로드)</p>
					</th>
					<td>
						<div class="file-url">
							<input type="text" placeholder="모든 파일 업로드 가능 (최대 10MB)">
							<!-- button type="btn-del">x</button -->
						</div>
						<div class="file multi">
							<input type="file" name="productFile" >
							<button type="button" >검색</button>
						</div>
						<div class="file-list">
						<%
						List<DesignPreviewImageVO> productList = itemVO.getImageList();
						for(DesignPreviewImageVO aProduct : productList ){
						%>
							<div><%=aProduct.getFilename() %> <button type="button" name="removeProductSeq" value="<%=aProduct.getSeq() %>" >x</button></div>
						<%	
						}
						%>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">오픈소스 등록
						<!-- 설명 -->
						<a href="javascript:void(0);" class="btn-guide"><img src="../resources/image/common/blt_guide.gif" alt="도움말"></a>
						<div class="modal-guide">
							<p class="txt-guide">작품 제작에 사용하거나<br>
							참고한 오픈소스 컨텐츠를<br>
							공유할 수 있도록 등록해주세요!</p>
						</div>
						<!-- //설명 -->
						<p class="p-guide">(등록한 순서대로 업로드)</p>
					</th>
					<td>
						<div class="file-url">
							<input type="text" id="titleOpenSource" maxlength="20" placeholder="오픈소스 설명 입력(최대 20자)" />
						</div>
						<div class="file-url">
							<input type="text" placeholder="모든 파일 업로드 가능 (최대 10MB)">
							<!-- button type="btn-del">x</button -->
						</div>
						<div class="file multi">
							<input type="file" name="openSourceFile">
							<button type="button">검색</button>
						</div>
						<div class="file-list">
						<%
						List<DesignWorkFileVO> openSourceList = itemVO.getFileList();
						for(DesignWorkFileVO aProduct : openSourceList ){
						%>
							<div><%=aProduct.getFilename() %> <button type="button" name="removeOpenSourceSeq" value="<%=aProduct.getSeq() %>" >x</button></div>
						<%	
						}
						%>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">디자인 설명</th>
					<td colspan="3"><textarea name="contents"  maxlength="1000" placeholder="디자인에 대해서 간단하게 설명해 주세요 (최대 1000자)"><%=StringUtil.nullToString(itemVO.getContents(), "") %></textarea></td>
				</tr>
			</table>
			</form>			
			<button id="btn_register" type="button" class="btn-complete" >수정완료</button>
			<button id="btn_delete" type="button" class="btn-complete" style="margin-right: 20px;" >삭제</button>
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

<!-- ################ 카테고리 ################### -->
<script >
	$(function(){
		new CategoryView({
			htmlContainer: $('#cateWrapProdMod')
			,hiddenFieldName: 'categoryCodes'
			,excludeData: {}
			,selCateCode: '<%=selCateCode%>'
		}).render();
	});
	
</script>
<!-- ################ ]]카테고리 ################### -->

<script type="text/javascript">
	$(function(){
		$('input[name="license01"]:eq(0)').prop('checked', true);
		$('input[name="license02"]:eq(0)').prop('checked', true);
		$('input[name="license03"]:eq(0)').prop('checked', true);
		
		var license = '<%=itemVO.getLicense()%>';
		if( license && license.length > 2 ){
			var licenseBY = license.substring(0, 1);
			$('input[name="license01"]').prop('checked', false);
			$('input[name="license01"][value="' + licenseBY + '"]').prop('checked', true);
			var licenseNC = license.substring(1, 2);
			$('input[name="license02"]').prop('checked', false);
			$('input[name="license02"][value="' + licenseNC + '"]').prop('checked', true);
			var licenseND = license.substring(2, 3);
			$('input[name="license03"]').prop('checked', false);
			$('input[name="license03"][value="' + licenseND + '"]').prop('checked', true);
			
		}
		
		$('#productForm').validate( {
			//각 항목 별로 validation rule을 지정한다.
            rules:{
            	title : { required: true }
				,cateDepth1 : { required: true }
				,fileUrlFile : { validateFileExtension: true  }
				, point: {required: true}
            },
            //rules에서 정의된 조건으로 validation에 실패했을 때 화면에 표시할 메시지를 지정한다.
            messages: {
            	title : { required: "디자인명을 입력해 주세요." }
            	,cateDepth1 : { required: "카테고리를 설정해주세요." }
            	,fileUrlFile : { validateFileExtension: "썸네일은 jpg, png 파일만 등록이 가능합니다." }
            	, point: {required: "포인트를 입력해 주세요."}
            },
            invalidHandler: function(form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors) {
                    alert(validator.errorList[0].message);
                    validator.errorList[0].element.focus();
                }          
           	},
          	//validator는 기본적으로 validation 실패 시 실패한 노드 우측에 실패 메시지를 표시하게 되어있다. 
          	//작동을 원하지 않으면 내용이 없는 errorPlacement를 선언한다.
          	//이 샘플에서는 alert를 사용하고 있으므로 빈 메서드를 선언하였다.
            errorPlacement: function(error, element) {
                // do nothing               	
            },
            ignore: [],
            focusInvalid:false,
            onfocusout: false,	// onblur 시 해당항목을 validation 할 것인지 여부 (default: true)
            onkeyup: false,
            onclick: false,
            debug:false, 			//true일 경우 validation 후 submit을 수행하지 않음. (default: false)
        } );
		

		$('#btn_register').on('click', function(e){
			e.preventDefault();
			updateProduct();
			
		});
		
		$('#btn_delete').on('click', function(e){
			e.preventDefault();
			deleteProduct();
			
		});
		
		$('input[name^="license0"]').on('click', function(e){
			var val = $(this).val();
			var name = $(this).prop('name');
				
			$('input[name="' + name + '"][value!="' + val + '"]').prop('checked', false);
			
		});
		
		$('.file.multi input[type="file"]').on('change', onChangeFile);
		
		$('.file-list button').on('click', function(){
			var val = $(this).val();
			var name = $(this).prop('name');
			$('#productForm').append($('<input type="hidden" name="' + name + '" value="' + val + '" />'));
			
			$(this).parent().remove();
			
		});
		
	});
	
	var onChangeFile = function(){
		var targetDiv = $(this).parent();
		
		var val = $(this).val();
		var name = $(this).prop('name');
		
		var isOpenSource = name.startsWith('openSourceFile'); //오픈 소스 인지 판단
		if(isOpenSource) {
			// 오픈 소스 특수처리
			var titleOpenSource = $('#titleOpenSource').val();
			if(titleOpenSource == '') {
				alert('오픈 소스 파일 이름을 입력하세요.');
				$(this).val('');
				return;
			}
			val = titleOpenSource;
			
		} else {
			var nameIndex = -1;
			if( val.lastIndexOf("\\") > -1 ){
				nameIndex = val.lastIndexOf("\\") + 1;
			}else if( val.lastIndexOf("/") > -1 ){
				nameIndex = val.lastIndexOf("/")  + 1;
				
			}
			
			if( nameIndex > -1 ){
				val = val.substring(nameIndex, val.length);					
			}
		}
		
		
		var div = null;
		if(isOpenSource) {
			div = $($.templates('<div >{{:value}} <input type="hidden" name="{{:filename}}" value="{{:filenameValue}}" /></div>').render({ 
					value: val
					,filename: 'filename_' + name
					,filenameValue: val
			}));
		} else {
			div = $($.templates('<div >{{:value}}</div>').render({ 
					value: val
			}));
		}
		
		var btn = $('<button type="button">x</button>');
		btn.on('click', function(){
			$(this).parent().remove();
		});
		//$(this).css('width', '0px');
		$(this).css('display', 'none');
		targetDiv.find('button').focus();
		div.append($(this));
		div.append(btn);
		
		targetDiv.next().append(div);
		targetDiv.append(createInputFile(name));
		
		// clear
		if(isOpenSource) {
			// 오픈 소스 특수처리
			$('#titleOpenSource').val('');
		}
		
	};
	
	function createInputFile(name){
		if( name && name.indexOf('_') > -1 ){
			name = name.substring(0, name.indexOf('_'));
		}
		var uid = name + '_' + guid();
		
		var file = $('<input type="file" name="' + uid + '" />');
		file.on('change', onChangeFile);
		return file; 
	}
	
	function guid() {
		function s4() {
	    	return Math.floor((1 + Math.random()) * 0x10000)
	      			.toString(16)
	      			.substring(1);
	  	}
	  	return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
	    		s4() + '-' + s4() + s4() + s4();
	}
	
	
	var floa_request = false;
	function deleteProduct(){
		
		if( floa_request ){
			return;
		}
		
		if( ! confirm('디자인을 삭제 하시겠습니까?') ){
			floa_request = false;
			return;
		}
		
		var form = $('#productForm');
		
		var seq = form.find('input[name="seq"]').val();
		$.ajax({
			url: '/product/deleteProduct.ajax', 
	        cache: false,
	        type: 'post',
	        data: {'seq': seq},
	        error : function(_data){
				console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			},
			success : function(_data){
				if(_data.result == '1') {
					window.location.href = '/product/product.do';
		    	} else if(_data.result == '100') {
		    		if( confirm('로그인이 필요합니다. 로그인 하시겠습니까?') ){
		    			modalShow('#login-modal');
		    		}
		    	} else if(_data.result == '101') {
		    		alert('수정 권한이 없습니다.');
		    	} else if(_data.result == '301') {
		    		alert('수정 권한이 없습니다.');
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    		
		    	}
			}
		});
	}
	
	function updateProduct(){
		
		var form = $('#productForm');
		if( ! form.valid() ){
			return;
		}
		
		var tag = form.find('input[name="tag"]');
		var tagVal = tag.val();
		var tags = tagVal.split(',');
		if( tags ){
			if( tags.length > 5 ){
				alert('태그 입력은 최대 5개까지 가능 합니다.');
				tag.focus();
				return;	
			}
			
			var flag = false;
			for(var i = 0; i < tags.length; i++){
				var aTag = tags[i];
				if( '' != $.trim(aTag) ){
					var stringByteLength = (function(s,b,i,c){
					    for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
					    return b
					})(aTag);
					
					if( stringByteLength > 20 ){
						flag = true;
						break;
					}
				}
			}
			
			if( flag ){
				alert('태그 입력은 개당 20byte를  넘을수  없습니다.');
				tag.focus();
				return;
			}
		}
		
		form.ajaxSubmit({
			url : "/product/updateProduct.ajax",
			type : "post",
			dataType : 'json',
			error : function(_data) {
				console.log(_data);
		    	alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
			},
			success : function(_data) {
				if(_data.result == '1') {
					window.location.href = '/product/product.do';
		    	} else if(_data.result == '100') { //이메일 중복
		    		if( confirm('로그인이 필요합니다. 로그인 하시겠습니까?') ){
		    			modalShow('#login-modal');
		    		}
		    	} else if(_data.result == '101') { //이메일 중복
		    		alert('수정 권한이 없습니다.');
		    	} else if(_data.result == '201') { //이메일 중복
		    		alert('썸네일 등록이 필요합니다.');		    		
		    	} else if(_data.result == '202') { //이메일 중복
		    		alert('썸네일 등록은 jpg,png만 등록 가능합니다.');
		    	} else if(_data.result == '203') { //이메일 중복
		    		alert('모든 파일 업로드는 최대 10MB까지 등록이 가능합니다.[' + _data.fileName + ']');
		    	} else if(_data.result == '301') {
		    		alert('상업적 이용이  허락되지 않음으로 설정 되어 있는 경우 포인트를 숫자로 입력해야 합니다.');
		    	} else {
		    		alert("오류가 발생 하였습니다.\n관리자에게 문의 하세요.");
		    	}
			}
		});
		
	}
	
	//버튼으로 태그 추가.
	function tagButtonClick(val) {
		var form = $("#productForm");
		var tag = form.find('input[name="tag"]');
		var tagVal = tag.val().trim();
		
		if(tagVal.indexOf(val) > -1) {
			return;
		} else {
			var newTagVal;
			
			if(tagVal.charAt(tagVal.length -1) == "," || tagVal.length == 0) {
				if(tagVal.length > 0) {
					newTagVal = tagVal + " " + val + ",";
				} else {
					newTagVal = tagVal + val + ",";	
				}
				
			} else {
				newTagVal = tagVal + ", " + val + ",";
			}
			
			tag.val(newTagVal);
		}
	} 
</script>
</body>
</html>