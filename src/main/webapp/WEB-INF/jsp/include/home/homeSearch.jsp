<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>
<script src="${pageContext.request.contextPath}/js/index.js"></script>
<script>
    $(function(){
        $("#keyword").keyup(function () {
            var value=($("#keyword").val().trim());
            if(value.length==0){
                 return;
            }
            $(".autoSearch").css("display", "none");
            $(".autoSearch").empty();
            var content=$("<ul></ul>").addClass("proposal-list");
            $.ajax({
                type:"post",
                url:  "${pageContext.request.contextPath}/searchProductName",
                data: {"keyword":value},
                dataType : "json",
                success: function(data){
                    $.each(data,function (i,v) {
                        if(data.length!=0 && i<6) {
                            var element = $("<li></li>").html(v).addClass("proposal").mouseenter(function () {
                                $(this).addClass('selected');
                            }).mouseleave(function () {
                                $(this).removeClass('selected');
                            }).click(function () {
                                $("#keyword").val($(this).html());
                                $(".autoSearch").empty();
                                $(".autoSearch").css("display","none");
                            });
                            content = content.append(element);
                        }
                    });
                    $(".autoSearch").append(content);
                }
            });
            if (0 != value.length ) {
                $(".autoSearch").css("display", "block");
            }
        });

        $(document).click(function () {
            $(".autoSearch").empty();
            $(".autoSearch").css("display","none");
        });

        $("#homeSearchForm").submit(function () {
            if ($("#keyword").val().trim().length == 0) {
                $("#keyword").attr("placeholder", "请输入搜索商品名称！");
                return false;
            }
            return true;
        });
    });
</script>

<header class="index-top">
    <a href="${pageContext.request.contextPath}" class="logo">
        <img src="img/logo.png">
    </a>
    <div class="search" style="position: relative">
        <form id="homeSearchForm" action="${pageContext.request.contextPath}/solrSearch " method="post" >
            <input id="keyword"  type="text"  name="keyword" placeholder="搜索iPhone" autocomplete="off" value="${param.keyword}" >
            <div class="autoSearch"><!--弹出搜索提示-->
            </div>
        <button class="search-button" type="submit">搜索</button>
        </form>
        <ul class="search-below">
            <c:forEach items="${categories}" var="c" varStatus="vs">
                <c:if test="${vs.count>=1 and vs.count<=8}">
                    <li><a href="${pageContext.request.contextPath}/category?id=${c.id}">${c.name}</a></li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</header>