<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- ********************* websocket ********************* -->
<%
	String wbReqUri = (String)request.getAttribute("javax.servlet.forward.request_uri");
	String wbQueryStr = (String)request.getAttribute("javax.servlet.forward.query_string");
	String wbCurUrl = wbReqUri + "?" + wbQueryStr;
	System.out.println(">>> wbCurUrl=" + wbCurUrl);
	// project 판단
	boolean isProject = false;
	if(wbReqUri.indexOf("/project/openProjectDetail.do") != -1) {
		isProject = true;
	}
%>
<script src="/resources/js/wd-websocket.js" ></script> <!-- websocket  -->
<script type="text/javascript">
	var ws = null; /* global */
    $(function() {
    	ws = new WebSocketWrapper({
        	uri: '<%=wbCurUrl%>'
        });
    	
    	console.log('>>> isProject=' + <%=isProject%>); 
    	<% if(isProject) {%>
    	ws.setProjectCallback(function(){
    		loadPage();
    	});
    	<% } %>
    	
    	ws.init();
    });
</script>
<!-- ********************* ]]websocket ********************* -->
