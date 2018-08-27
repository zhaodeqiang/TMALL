<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>
<c:if test="${!empty user}">
    <script>
        $(function () {
            $.get("cartNumber", function (result) {
                var number = Number(result);
                if (number > -1) {
                    $("#cart-number").text(number);
                } else {
                    $("#cart-number").text(0);
                }
            });
        });

    </script>
</c:if>

<nav class="top">
    <div class="top-bar">
        <span class="top-left">
            <c:if test="${empty home}">
                <span style="margin-left: 0"><a href="${pageContext.request.contextPath}"><span
                        class=" glyphicon glyphicon-home redColor" style="margin-left: 0"></span></a><a
                        href="${pageContext.request.contextPath}">首页</a></span>
            </c:if>
            <span>欢迎来到${website_name}</span>
            <c:if test="${!empty user}">
                <span style="color: red;">${user.username}</span>
                <a href="${pageContext.request.contextPath}/forgetPassword?resetType=1">修改密码</a>
                <a href="${pageContext.request.contextPath}/logout">退出</a>
            </c:if>
            <c:if test="${empty user}">
                您好!<a href="${pageContext.request.contextPath}/loginIn" id="login">请登录</a>
                <a href="${pageContext.request.contextPath}/register">免费注册</a>
            </c:if>
        </span>

        <span class="pull-right">
            <c:if test="${!empty user}">
                <a href="${pageContext.request.contextPath}/myOrder">我的订单</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/cart"><span
                    class=" glyphicon glyphicon-shopping-cart redColor"></span>
                购物车<c:if test="${!empty user}"><strong id="cart-number"></strong>件</c:if></a>
            <c:if test="${user.group=='admin' || user.group=='superAdmin'}">
                <a href="${pageContext.request.contextPath}/admin/">网站后台</a>
            </c:if>
        </span>
    </div>
</nav>