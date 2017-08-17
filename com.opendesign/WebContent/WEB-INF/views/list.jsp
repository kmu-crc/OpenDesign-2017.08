<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>스프링프레임워크 게시판</title>
  </head>
  <body>
  <table border="1">
       <colgroup>
     <col width="60"><col><col width="115"><col width="85">
     </colgroup>
     <thead>
       <tr>
            <th scope="col">제목</th>
            <%String param11 = (String) request.getAttribute("uname");
            out.println(param11 );%>
            <br>
            <th scope="col">내용</th>
            <%String param22 = (String) request.getAttribute("text");
            out.println(param22);%>
       </tr>
       </thead>
       <tbody>
       <!-- 목록이 반복될 영역 -->
       <tr>
            <td>번호</td>
            <td>제목</td>
            <td>작성자</td>
            <td>등록일</td>
       </tr>
       </tbody>
  </table>
  <div><a href="./user">새글 쓰기</a></div>
  </body>
</html>