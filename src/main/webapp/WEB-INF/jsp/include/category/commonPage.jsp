<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="search">
    <div class="items">
        <c:forEach items="${products}" var="p" varStatus="vs">
            <a href="${pageContext.request.contextPath}/product?id=${p.id}">
                <div class="border" price="${p.nowPrice}">
                    <div class="item">
                        <div class="content">
                            <img class="item-img" src="${productImgDir}${p.image.path}">
                            <div class="item-price">
                                ￥${p.nowPrice}
                            </div>
                            <div class="item-title">
                                    ${p.subTitle}
                            </div>
                            <div class="item-shop">
                            </div>
                            <div class="item-bottom">
                        <span>
                            月成交<em>${p.saleCount}笔</em>
                        </span>
                                <span>
                            评价<a href="product?product.id=${p.id}">${p.commentCount}条</a>
                        </span>
                                <span><img src="img/wangwang.png"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
    <c:if test="${empty products}">
        <div class="no-match">没有满足条件的产品</div>
    </c:if>
    <!-- 分页-->
    <c:if test="${!empty pagination&&pagination.totalPage>0}">
        <div class="container text-center" style="clear: both">
            <nav aria-label="...">
                <ul class="pagination">
                    <c:if test="${pagination.hasPrevious==false}">
                        <li class="page-item disabled">
                            <a class="page-link" href="javascript:void(0);" tabindex="-1">«</a>
                        </li>
                        <li class="page-item disabled">
                            <a class="page-link" href="javascript:void(0)" tabindex="-1">‹</a>
                        </li>
                    </c:if>
                    <c:if test="${pagination.hasPrevious==true}">
                        <li class="page-item ">
                            <a class="page-link" href="?start=0${pagination.param}&keyword=${keyword}"
                               tabindex="-1">«</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link"
                               href="?start=${pagination.start-pagination.count<0?0:pagination.start-pagination.count}${pagination.param}&keyword=${keyword}"
                               tabindex="-1">‹</a>
                        </li>
                    </c:if>
                    <c:forEach begin="0" end="${pagination.totalPage-1}" varStatus="vs">
                        <li class="page-item ${vs.index * pagination.count == pagination.start ? 'active':''}">
                            <a class="page-link"
                               href="?start=${vs.index*pagination.count}${pagination.param}&keyword=${keyword}">
                                    ${vs.count}
                            </a>
                        </li>
                    </c:forEach>
                    <c:if test="${pagination.hasNext==false}">
                        <li class="page-item disabled">
                            <a class="page-link" href="javascript:void(0);">›</a>
                        </li>
                        <li class="page-item disabled">
                            <a class="page-link" href="javascript:void(0);">»</a>
                        </li>
                    </c:if>
                    <c:if test="${pagination.hasNext==true}">
                        <li class="page-item">
                            <a class="page-link"
                               href="?start=${pagination.start+pagination.count}${pagination.param}&keyword=${keyword}">›</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link"
                               href="?start=${pagination.lastPage}${pagination.param}&keyword=${keyword}">»</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </c:if>
</main>


