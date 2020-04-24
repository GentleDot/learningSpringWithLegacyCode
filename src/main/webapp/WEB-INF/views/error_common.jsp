<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error_Detail</title>
</head>
<body>
   <h1>오류 확인 페이지</h1>
   <h4>${exception.getMessage() }</h4>

   <ul>
   <c:forEach items="${exception.getStackTrace() }" var="stack">
     <li>${stack.toString()}</li>
   </c:forEach>
   </ul>
</body>
</html>