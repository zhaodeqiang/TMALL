<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>


<section class="head-bar">
    <div class="c-menu">
        <span class="glyphicon glyphicon-th-list icon"></span>
        <span>商品分类</span>
    </div>
    <div class="r-menu">
        <a style="cursor: pointer"><img src="img/chaoshi.png"></a>
        <a style="cursor: pointer"><img src="img/guoji.png"></a>
        <c:forEach items="${categories}" var="c" varStatus="vs">
            <c:if test="${vs.count>=1 and vs.count<=4}">
                <a href="${pageContext.request.contextPath}/category?id=${c.id}">${c.name}</a>
            </c:if>
        </c:forEach>
    </div>
</section>

<section class="carousel">
    <div data-ride="carousel" class="carousel-of-product carousel slide" id="carousel-of-product">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li class="active" data-slide-to="0" data-target="#carousel-of-product"></li>
            <li data-slide-to="1" data-target="#carousel-of-product" class=""></li>
            <li data-slide-to="2" data-target="#carousel-of-product" class=""></li>
            <li data-slide-to="3" data-target="#carousel-of-product" class=""></li>
        </ol>
        <!-- Wrapper for slides -->
        <%--可以做成动态数据，从数据库查询，方便维护--%>
        <div role="listbox" class="carousel-inner">
            <div class="item active">
                <a href="${pageContext.request.contextPath}/product?id=14" target="_blank">
                    <img src="${pageContext.request.contextPath}/img/1.jpg" class="carousel carousel-image">
                </a>
            </div>
            <div class="item">
                <a href="${pageContext.request.contextPath}/product?id=1" target="_blank">
                    <img src="img/2.jpg" class="carousel-image">
                </a>
            </div>
            <div class="item">
                <a href="${pageContext.request.contextPath}/product?id=3" target="_blank">
                    <img src="${pageContext.request.contextPath}/img/3.png" class="carousel-image">
                </a>
            </div>
            <div class="item">
                <a href="${pageContext.request.contextPath}/product?id=30" target="_blank">
                    <img src="img/4.png" class="carousel-image">
                </a>
            </div>
        </div>

        <div class="m-menu">
            <ul>
                <c:forEach items="${categories}" var="c" varStatus="vs">
                    <c:if test="${vs.count>=1 and vs.count<=13}">
                        <li cid="${c.id}"><span class="glyphicon glyphicon-link"></span><a
                                href="${pageContext.request.contextPath}/category?id=${c.id}">${c.name}</a></li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
        <c:forEach items="${categories}" var="c" varStatus="vs">
            <div class="d-menu" cid="${c.id}" style="display: none">
                <c:forEach items="${c.products}" var="p" varStatus="vs">
                    <a href="${pageContext.request.contextPath}/product?id=${p.id}">${p.name}</a>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

</section>
