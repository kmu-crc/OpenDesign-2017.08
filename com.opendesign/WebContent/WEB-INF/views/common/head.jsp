<%@page import="java.util.Date"%>
<%@page import="com.opendesign.utils.PropertyUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>open src Design</title>
<link rel="stylesheet" type="text/css" href="/resources/css/common.css">
<link rel="stylesheet" type="text/css" href="/resources/css/layout.css?ver=1013">
<link rel="stylesheet" type="text/css" href="/resources/css/jquery-ui-1.12.1.css">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/jquery-ui.css">-->
<script src="/resources/js/jquery-3.1.0.min.js"></script> 	<!-- for product -->
<!-- <script src="/resources/js/jquery-3.1.0.js"></script> -->				<!-- for develop -->
<script src="https://use.fontawesome.com/d36a61a405.js"></script>
<script src="/resources/js/swiper.min.js"></script>
<script src="/resources/js/jquery.validate.min.js"></script>		
<script src="/resources/js/jquery.form.js"></script>
<script src="/resources/js/jquery-ui.js"></script>	
<script src="/resources/js/jquery-ui-1.12.1.js"></script>	
<script src="/resources/js/jsrender.min.js"></script> <!-- js template -->

<!-- readonly -->
<script type="text/javascript" src="/resources/js/jquery.readonly.min.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/js/jquery.readonly.css">
<style type="text/css"> 
	.readonly_overlay {opacity: 0} 
</style>
<!-- ]]readonly -->

<script src="/resources/js/common.js"></script> <!-- util -->
<script src="/resources/js/wd-components.js"></script> <!-- custom template -->
<script src="/resources/js/wd-project-view.js"></script> <!-- project view -->
<script src="/resources/js/wd-category-view.js"></script> <!-- 카테고리 view -->
<script src="/resources/js/ui.jsp.js"></script> <!-- (ui.js html쪽과 충돌하지 않기 위해) -->
<script>
	/** wsGlobalHost: ws://localhost:8080 */
	var wsGlobalHost = '<%=PropertyUtil.getProperty("ws.global.host")%>';
</script>
<!-- ********************* websocket ********************* -->
<%@include file="/WEB-INF/views/common/inc_websocket.jsp"%> 
<!-- ********************* ]]websocket ********************* -->
