<%--
  Created by IntelliJ IDEA.
  User: ZDQ
  Date: 2018/6/29
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>

<c:set var="title" value="导入索引库"/>
<%@include file="common/adminHeader.jsp" %>
<c:set var="light" value="5"/>
<%@include file="common/adminNavigator.jsp" %>

<div class="container">
    <div class="form-group" style="height: 500px">
        <div style="text-align: center">
            <button onclick="importIndex()" class="btn btn-success btn-sm">一键导入索引库</button>
        </div>
    </div>
</div>
