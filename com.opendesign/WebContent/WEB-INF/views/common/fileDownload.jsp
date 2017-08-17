<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.lang.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%
	request.setCharacterEncoding("UTF-8");

	//=== test ====
	// 파일 업로드된 경로
	String root = request.getSession().getServletContext().getRealPath("/");
	//String filePath = root + "images/mpb/common";
	// 서버에 실제 저장된 파일명
	//String fileName = "approve_icon.jpg";
	// 실제 내보낼 파일명
	//String orgFileName = "approve_icon.jpg";
	//=== ]]test ====
	
	String fileUrl = request.getParameter("fileUrl");	// db path name
	String dispName = request.getParameter("dispName");	// 실제 내보낼 파일명
	
	//
	String realFilePathName = root + fileUrl; // 서버에 실제  저장된 경로+이름 
	String orgFileName = dispName;
	String ext = "." + FilenameUtils.getExtension(fileUrl);
	if(dispName != null && !dispName.endsWith(ext)) {
		orgFileName = orgFileName + ext;
	}
	
	System.out.println(">>> fileUrl=" + fileUrl);
	System.out.println(">>> dispName=" + dispName);
	System.out.println(">>> realFilePathName=" + realFilePathName);
	//=====================================

	InputStream in = null;
	OutputStream os = null;
	File file = null;
	boolean skip = false;
	String client = "";

	try {
		// fix for error: java.lang.IllegalStateException: getOutputStream() has already been called for this response
		out.clear();
		out = pageContext.pushBody(); 

		// 파일을 읽어 스트림에 담기
		try {
			file = new File(realFilePathName);
			in = new FileInputStream(file);
		} catch (FileNotFoundException fe) {
			skip = true;
		}

		client = request.getHeader("User-Agent");

		// 파일 다운로드 헤더 지정
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Description", "JSP Generated Data");

		if (!skip) {

			// IE
			if (client.indexOf("MSIE") != -1) {
				response.setHeader(
						"Content-Disposition",
						"attachment; filename="
								+ orgFileName);

			} else {
				// 한글 파일명 처리
				orgFileName = new String(orgFileName.getBytes("utf-8"),
						"iso-8859-1");
				
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + orgFileName + "\"");
				response.setHeader("Content-Type",
						"application/octet-stream; charset=utf-8");
			}

			response.setHeader("Content-Length", "" + file.length());

			os = response.getOutputStream();
			byte b[] = new byte[(int) file.length()];
			int leng = 0;

			while ((leng = in.read(b)) > 0) {
				os.write(b, 0, leng);
			}

		} else {
			response.setContentType("text/html;charset=UTF-8");
			out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");

		}

		in.close();
		os.close();

	} catch (Exception e) {
		e.printStackTrace();
	}
%>