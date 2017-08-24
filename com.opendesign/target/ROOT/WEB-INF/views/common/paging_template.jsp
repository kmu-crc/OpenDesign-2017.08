<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- ############### 페이징 template ###################### -->
<script id="tmpl-pagingTemplate" type="text/x-jsrender">
			<div class="paging" >
				<a href="javascript:void(0);"   data-page="{{:firstIndex}}" ><img src="/resources/image/sub/btn_first.png" alt="처음"></a>
				<a href="javascript:void(0);"   data-page="{{:prevIndex}}"><img src="/resources/image/sub/btn_prev.png" alt="이전"></a>
				{{for pageIndexList}}
					{{if #data == ~root.pageNo }}
				<a href="javascript:void(0);"   class="active" data-page="{{:#data}}">{{:#data}}</a>
					{{else}}
				<a href="javascript:void(0);"   data-page="{{:#data}}">{{:#data}}</a>
					{{/if}}
				{{/for}}
				<a href="javascript:void(0);"   data-page="{{:nextIndex}}"><img src="/resources/image/sub/btn_next.png" alt="다음"></a>
				<a href="javascript:void(0);"   data-page="{{:lastIndex}}"><img src="/resources/image/sub/btn_last.png" alt="마지막"></a>
			</div>
</script>
	
<%-- sample:
			<div class="paging" >
				<a href="javascript:void(0);" onclick="goPage(this);" data-page="1" ><img src="/resources/image/sub/btn_first.png" alt="처음"></a>
				<a href="javascript:void(0);" onclick="goPage(this);" data-page="1"><img src="/resources/image/sub/btn_prev.png" alt="이전"></a>
				<a href="javascript:void(0);" onclick="goPage(this);" data-page="1">1</a>
				<a href="javascript:void(0);" onclick="goPage(this);" data-page="2">2</a>
				<a href="javascript:void(0);" onclick="goPage(this);" data-page="3">3</a>
				<a href="javascript:void(0);" onclick="goPage(this);" data-page="4">4</a>
				<a href="javascript:void(0);" onclick="goPage(this);" data-page="5">5</a>
				<span>.....</span>
				<a href="#">40</a>
				<a href="javascript:void(0);" onclick="goPage(this);" data-page="1"><img src="/resources/image/sub/btn_next.png" alt="다음"></a>
				<a href="javascript:void(0);" onclick="goPage(this);" data-page="1"><img src="/resources/image/sub/btn_last.png" alt="마지막"></a>
			</div>
--%>		
<!-- ############### ]]페이징 template ###################### -->