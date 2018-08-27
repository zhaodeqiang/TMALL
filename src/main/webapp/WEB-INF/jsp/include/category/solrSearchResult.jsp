<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="js/search.js"></script>

<section class="select-bar">
    <a href="${pageContext.request.contextPath}/solrSearch?keyword=${keyword}&start=${pagination.start}"
       class="${empty param.sort?'selected':''}">综合<span class="glyphicon glyphicon-arrow-down"></span></a>
    <a href="${pageContext.request.contextPath}/solrSearch?keyword=${keyword}&sort=${param.sort=='comment'?'commentInverse':'comment'}&start=${pagination.start}"
       class="${(param.sort=='comment'||param.sort=='commentInverse')?'selected':''}">人气<span class="glyphicon glyphicon-arrow-down"></span></a>
    <a href="${pageContext.request.contextPath}/solrSearch?keyword=${keyword}&sort=${param.sort=='date'?'dataInverse':'date'}&start=${pagination.start}"
       class="${(param.sort=='date'||param.sort=='dataInverse')?'selected':''}">新品<span class="glyphicon glyphicon-arrow-down"></span></a>
    <a href="${pageContext.request.contextPath}/solrSearch?keyword=${keyword}&sort=${param.sort=='saleCount'?'saleCountInverse':'saleCount'}&start=${pagination.start}"
       class="${(param.sort=='saleCount'||param.sort=='saleCountInverse')?'selected':''}">销量<span class="glyphicon glyphicon-arrow-down"></span></a>
    <a href="${pageContext.request.contextPath}/solrSearch?keyword=${keyword}&sort=${param.sort=='price'?'priceInverse':'price'}&start=${pagination.start}"
       class="${(param.sort=='price'||param.sort=='priceInverse')?'selected':''}">价格<span
            class="glyphicon glyphicon-resize-vertical"></span></a>
    <span class="price">
        <input type="text" placeholder="￥请输入" class="sortBarPrice beginPrice" id="low_price">
        <span>-</span>
        <input type="text" placeholder="￥请输入" class="sortBarPrice beginPrice" id="high_price">
    </span>
</section>

<main class="search">
    <div class="items">
        <c:forEach items="${solrSearchResult}" var="ssr" varStatus="vs">
            <a href="${pageContext.request.contextPath}/product?id=${ssr.id}">
                <div class="border" price="${ssr.nowPrice}">
                    <div class="item">
                        <div class="content">
                            <img class="item-img" src="${productImgDir}${ssr.imgPath}">
                            <div class="item-price">
                                ￥${ssr.nowPrice}
                            </div>
                            <div class="item-title">
                                    ${ssr.subTitle}
                            </div>
                            <div class="item-shop">
                            </div>
                            <div class="item-bottom">
                        <span>
                            月成交<em>${ssr.saleCount}笔</em>
                        </span>
                                <span>
                            评价<a href="product?product.id=${ssr.id}">${ssr.commentCount}条</a>
                        </span>
                                <span><img src="img/wangwang.png"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
    <c:if test="${empty solrSearchResult}">
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


