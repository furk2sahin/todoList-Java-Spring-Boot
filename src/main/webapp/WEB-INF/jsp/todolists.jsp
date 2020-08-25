<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Insert title here</title>
</head>
<style>
    td{margin: 1px; border-style: solid; border-color: black; text-align: center; padding: 3px}
</style>
<body>
<table style="border:black; background-color: beige; border-style: solid; padding: 5px">
    <thead>
    <tr>
        <td>ID</td>
        <td>Title</td>
        <td>Create Date</td>
    </tr>
    </thead>
    <c:forEach items="${todolists}" var="todolist" varStatus="status">
        <tr bgcolor="${status.index % 2 == 0 ? 'white' : 'lightgray'}">

            <td>${todolist.id}</td>
            <td>${todolist.title}</td>
            <td>${todolist.createDate}</td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty message}"/>
<div style = "color:blue">
    ${message}
</div>
</body>
</html>