<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.opendesign.utils.CmnUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.opendesign.vo.CategoryVO"%>
<%@page import="com.opendesign.utils.StringUtil"%><%
	//카테고리 파라미터
	String schCate = request.getParameter("schCate");
	String codeDepth1 = CmnUtil.getCodeDepth1(schCate);
	//현재 URL를 가져온다.
	String currentUrl = (String)request.getAttribute("javax.servlet.forward.request_uri");
	//카테고리:
	List<CategoryVO> cateListDepth1 = (List<CategoryVO>)request.getAttribute("cateListDepth1");
	
	List<CategoryVO> cateListDepth2 = (List<CategoryVO>)request.getAttribute("cateListDepth2");
	List<CategoryVO> cateListDepth3 = (List<CategoryVO>)request.getAttribute("cateListDepth3");
	
	Map<String, List> cateMapDepth2 = new HashMap();
	
	for( CategoryVO cate1 : cateListDepth1 ) {
		
		String parentCode = cate1.getCategoryCode();
		for( CategoryVO cate2 : cateListDepth2 ) {
			
			String referCode = cate2.getCategoryCode().substring(0, 3);
			if( parentCode.equals(referCode) ) {
				List<CategoryVO> cate2List = cateMapDepth2.get(parentCode);
				if( cate2List == null ) {
					cate2List = new ArrayList();
					cateMapDepth2.put(parentCode, cate2List);
				}
				cate2List.add(cate2);
			}
		}
	}
	
	Map<String, List> cateMapDepth3 = new HashMap();
	
	for( CategoryVO cate2 : cateListDepth2 ) {
		
		String parentCode = cate2.getCategoryCode();
		for( CategoryVO cate3 : cateListDepth3 ) {
			
			String referCode = cate3.getCategoryCode().substring(0, 6);
			if( parentCode.equals(referCode) ) {
				List<CategoryVO> cate3List = cateMapDepth3.get(parentCode);
				if( cate3List == null ) {
					cate3List = new ArrayList();
					cateMapDepth3.put(parentCode, cate3List);
				}
				cate3List.add(cate3);
			}
		}
		
	}
	
%>
				<!-- 카테고리 -->
				<%
					if(cateListDepth1 != null) {
						for( CategoryVO item : cateListDepth1 ) {
				%>
				<li id="<%=item.getCategoryCode()%>" style="display:none;" class="<%=StringUtil.equals(item.getCategoryCode(), codeDepth1, "active") %>">
					<a href="javascript:searchCategory('<%=item.getCategoryCode()%>');" data-code="<%=item.getCategoryCode()%>" ><%=item.getCategoryName()%></a>
					
					<%
					List<CategoryVO> cate2List = cateMapDepth2.get(item.getCategoryCode());
					if( cate2List != null && cate2List.size() > 0 ) {
					
						out.println("<ul class='depth3'>");
							for( CategoryVO cate2 : cate2List ) {
								String parentCode = cate2.getCategoryCode();
								List<CategoryVO> cate3List = cateMapDepth3.get(parentCode);
								String classHasSub = (cate3List != null && cate3List.size() > 0) ? "sub" : "";
								out.println("<li class='" + classHasSub + "'><a href='javascript:searchCategory(\""+cate2.getCategoryCode()+"\")'>" + cate2.getCategoryName() + "</a>");
								if( cate3List != null && cate3List.size() > 0 ) {
									out.println("<ul class='depth4'>");
									for( CategoryVO cate3 : cate3List ) {
										out.println("<li class=''><a href='javascript:searchCategory(\""+cate3.getCategoryCode()+"\")'>" + cate3.getCategoryName() + " </a></li>");
									}
									out.println("</ul>");
								}
								out.println("</li>");
							}
						out.println("</ul>");
					}
					
						} //for loop cateListDepth1
					} //cateListDepth1 null check
				%>
				