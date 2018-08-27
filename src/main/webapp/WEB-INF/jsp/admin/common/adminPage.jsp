<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>

<c:if test="${!empty pagination&&pagination.totalPage>0}">
    <div class="container text-center">
        <nav aria-label="...">
            <ul class="pagination">
                <c:if test="${pagination.hasPrevious==false}">
                    <li class="page-item disabled">
                        <a class="page-link" href="javascript:void(0);" tabindex="-1">«</a>
                    </li>
                    <li class="page-item disabled">
                        <a class="page-link" href="javascript:void(0);" tabindex="-1">‹</a>
                    </li>
                </c:if>
                <c:if test="${pagination.hasPrevious==true}">
                    <li class="page-item ">
                        <a class="page-link" href="?start=0${pagination.param}" tabindex="-1">«</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="?start=${pagination.start-pagination.count<0?0:pagination.start-pagination.count}${pagination.param}"
                           tabindex="-1">‹</a>
                    </li>
                </c:if>
                <c:forEach begin="0" end="${pagination.totalPage-1}" varStatus="vs">
                    <li class="page-item ${vs.index * pagination.count == pagination.start ? 'active':''}">
                        <a class="page-link"
                           href="?start=${vs.index*pagination.count}${pagination.param}">${vs.count}</a>
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

                    <li class="page-item ">
                        <a class="page-link" href="?start=${pagination.start+pagination.count}${pagination.param}">›</a>
                    </li>
                    <li class="page-item ">
                        <a class="page-link" href="?start=${pagination.lastPage}${pagination.param}">»</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</c:if>