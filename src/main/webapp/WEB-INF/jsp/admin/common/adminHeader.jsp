<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>
<!DOCTYPE html>
<html>
<head>
    <title>${title} - 商城后台</title>
    <meta charset="utf-8">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/toast.css">

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/toast.js"></script>

    <script>
        function checkEmpty(form) {
            var flag = true;
            /**
             * @Author       ZDQ
             * @MethodName
             * @Description  校验文本框Text内容是否为空
             * @Param
             * @Return
             * @Exception
             * @Date         2018/6/12 17:20
             */
            $(form).find("input[type=text]").each(function () {
                var value = $(this).val();
                if (value.length === 0) {
                    $('body').toast({
                        content: '文本框不能为空！',
                        duration: 1000,
                        isCenter: true,
                        background: 'rgba(230,0,0,0.5)',
                        animateIn: 'bounceIn-hastrans',
                        animateOut: 'bounceOut-hastrans',
                    });
                    $(this).focus();
                    flag = false;
                    return flag;
                }
            });
            return flag;
        }

        $(function () {
            $("#add-form").submit(function () {
                if (!checkEmpty(this)) {
                    return false;
                }
            });
            $(".delete-button").click(function () {
                return !!confirm("确实删除？");
            });
            $(".detail-btn").click(function () {
                $(this).parents("tr").next().toggle();
            });
        });

        function importIndex() {
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/TMALL/admin/solr/importSolrIndex",
                success: function (data) {
                    if (data == "true") {
                        $('body').toast({
                            content: '导入成功！',
                            duration: 1000,
                            isCenter: true,
                            background: 'rgba(230,0,0,0.5)',
                            animateIn: 'bounceIn-hastrans',
                            animateOut: 'bounceOut-hastrans',
                        });
                    } else {
                        $('body').toast({
                            content: '导入失败！',
                            duration: 1000,
                            isCenter: true,
                            background: 'rgba(230,0,0,0.5)',
                            animateIn: 'bounceIn-hastrans',
                            animateOut: 'bounceOut-hastrans',
                        });
                    }
                }
            });
        }

        /* $.post("http://localhost:8080/TMALL/admin/solr/importSolrIndex", null, function (data) {
        //URL必须写全
             if (data == "true") {
                 $('body').toast({
                     content: '导入成功！',
                     duration: 1000,
                     isCenter: true,
                     background: 'rgba(230,0,0,0.5)',
                     animateIn: 'bounceIn-hastrans',
                     animateOut: 'bounceOut-hastrans',
                 });
             } else {
                 $('body').toast({
                     content: '导入失败！',
                     duration: 1000,
                     isCenter: true,
                     background: 'rgba(230,0,0,0.5)',
                     animateIn: 'bounceIn-hastrans',
                     animateOut: 'bounceOut-hastrans',
                 });
             }
         });*/

    </script>
</head>
<body>


