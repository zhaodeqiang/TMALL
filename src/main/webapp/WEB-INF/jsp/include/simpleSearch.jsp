<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="${pageContext.request.contextPath}/js/index.js"></script>
<script>
    $(function () {
        $("#keyword").keyup(function () {
            var value = ($("#keyword").val().trim());
            if (value.length == 0) {
                return;
            }
            $(".simpleAutoSearch").css("display", "none");
            $(".simpleAutoSearch").empty();
            var content = $("<ul></ul>").addClass("proposal-list");
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/searchProductName",
                data: {"keyword": value},
                dataType: "json",
                success: function (data) {
                    $.each(data, function (i, v) {
                        if (data.length != 0 && i < 6) {
                            var element = $("<li></li>").html(v).addClass("proposal").mouseenter(function () {
                                $(this).addClass('selected');
                            }).mouseleave(function () {
                                $(this).removeClass('selected');
                            }).click(function () {
                                $("#keyword").val($(this).html());
                                $(".simpleAutoSearch").empty();
                                $(".simpleAutoSearch").css("display", "none");
                            });
                            content = content.append(element);
                        }
                    });
                    $(".simpleAutoSearch").append(content);
                }
            });
            if (0 != value.length) {
                $(".simpleAutoSearch").css("display", "block");
            }
        });

        $(document).click(function () {
            $(".simpleAutoSearch").empty();
            $(".simpleAutoSearch").css("display", "none");
        });

        $("#simpleSearch").submit(function () {
            if ($("#keyword").val().trim().length == 0) {
                $("#keyword").attr("placeholder", "请输入搜索商品名称！");
                return false;
            }
            return true;
        });
    });
</script>

<header class="product">
    <a href="${pageContext.request.contextPath}" class="logo">
        <img src="img/logo2.png">
    </a>
    <div class="search" style="position: relative">
        <form action="${pageContext.request.contextPath}/solrSearch" id="simpleSearch" method="post">
            <input type="text" placeholder="搜索 ${website_name} 商品/品牌/店铺" name="keyword" autocomplete="off" id="keyword" value="${param.keyword}">
            <div class="simpleAutoSearch"><!--弹出搜索提示-->
            </div>
            <button class="search-button" type="submit">搜索</button>
        </form>
        <ul class="search-below">
            <c:forEach items="${cs}" var="c" varStatus="vs">
                <li><a href="${pageContext.request.contextPath}/category?id=${c.id}">${c.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</header>