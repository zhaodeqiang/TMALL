<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>出错了</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>

<%@include file="include/top.jsp" %>
<main class="search" style="margin-bottom: 10%;margin-top: 5%;">
    <div class="alert alert-warning" role="alert">
        <img src="${pageContext.request.contextPath}/img/error.png">
        <div style="float: right;width: 500px;height: 333px ;">
            <div style="width: auto;padding-left: 100px;padding-top: 100px">
                <p style="font-size: medium">您所访问的资源不存在!</p>
                <a href="${pageContext.request.contextPath}">返回首页</a>
            </div>
        </div>
    </div>

</main>
<%@include file="include/footer.jsp" %>
