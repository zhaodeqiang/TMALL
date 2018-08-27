<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="js/search.js"></script>

<section class="select-bar">
    <a href="${pageContext.request.contextPath}/category?id=${category.id}&start=${pagination.start}"
       class="${empty param.sort?'selected':''}">综合<span class="glyphicon glyphicon-arrow-down"></span></a>
    <a href="${pageContext.request.contextPath}/category?id=${category.id}&sort=${param.sort=='comment'?'commentInverse':'comment'}&start=${pagination.start}"
       class="${(param.sort=='comment'||param.sort=='commentInverse')?'selected':''}">人气<span class="glyphicon glyphicon-arrow-down"></span></a>
    <a href="${pageContext.request.contextPath}/category?id=${category.id}&sort=${param.sort=='date'?'dateInverse':'date'}&start=${pagination.start}"
       class="${(param.sort=='date'||param.sort=='dateInverse')?'selected':''}">新品<span class="glyphicon glyphicon-arrow-down"></span></a>
    <a href="${pageContext.request.contextPath}/category?id=${category.id}&sort=${param.sort=='saleCount'?'saleCountInverse':'saleCount'}&start=${pagination.start}"
       class="${(param.sort=='saleCount'||param.sort=='saleCountInverse')?'selected':''}">销量<span class="glyphicon glyphicon-arrow-down"></span></a>
    <a href="${pageContext.request.contextPath}/category?id=${category.id}&sort=${param.sort=='price'?'priceInverse':'price'}&start=${pagination.start}"
       class="${(param.sort=="price"||param.sort=="priceInverse")?'selected':''}">价格<span
            class="glyphicon glyphicon-resize-vertical"></span></a>
    <span class="price">
        <input type="text" placeholder="￥请输入" class="sortBarPrice beginPrice" id="low_price">
        <span>-</span>
        <input type="text" placeholder="￥请输入" class="sortBarPrice beginPrice" id="high_price">
    </span>
    <%--<span style="float: right;text-align: center;font-size: smaller;color: #666;">共件商品</span>--%>
</section>

<%@include file="commonPage.jsp" %>