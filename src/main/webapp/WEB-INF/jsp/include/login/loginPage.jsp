<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(function () {
        var msg = "${msg}";
        if (msg !== "") {
            $("#J-message p").html(msg);
            $("#message").css("display", "none");
            $(".login-tip").css("display", "none");
            $("#J-message").css("display", "block");
        }

        /*if (!$("#refer").val()) {
            //如果refer的值为空，则将上一个页面的URL赋值给refer隐藏域
            $("#refer").val(document.referrer);//上一个页面的URL
        }*/
        $("#form").submit(function () {
            //校验之前全部清空
            $("#message").css("display", "none");
            $(".login-tip").css("display", "none");
            $("#J-message").css("display", "none");
            if ($("#password").val().trim() == "" && $("#name").val().trim() == "" && $("#captcha").val().toLowerCase() == "") {
                $("#message").html("请输入验证码！");
                $("#J-message p").html("会员名和密码不能为空！");
                $("#message").css("display", "block");
                $("#J-message").css("display", "block");
                return false;
            }
            if ($("#password").val().trim().length != 0 && $("#name").val().trim() == "" && $("#captcha").val().toLowerCase() == "") {
                $("#J-message p").html("会员名不能为空！");
                $("#message").html("请输入验证码！");
                $("#message").css("display", "block");
                $("#J-message").css("display", "block");
                return false;
            }
            if ($("#password").val().trim().length == 0 && $("#name").val().trim() != "" && $("#captcha").val().toLowerCase() == "") {
                $("#J-message p").html("密码不能为空！");
                $("#message").html("请输入验证码！");
                $("#message").css("display", "block");
                $("#J-message").css("display", "block");
                return false;
            }
            if ($("#password").val().trim().length == 0 && $("#name").val().trim() == "" && $("#captcha").val().toLowerCase() != "") {
                $("#J-message p").html("会员名和密码不能为空！");
                $("#J-message").css("display", "block");
                return false;
            }
            if ($("#password").val().trim().length == 0 && $("#name").val().trim() != "" && $("#captcha").val().toLowerCase() != "") {
                $("#J-message p").html("密码不能为空！");
                $("#J-message").css("display", "block");
                return false;
            }
            if ($("#name").val().trim().length == 0 && $("#password").val().trim() != "" && $("#captcha").val().toLowerCase() != "") {
                $("#J-message p").html("会员名不能为空！");
                $("#J-message").css("display", "block");
                return false;
            }
            if ($("#name").val().trim().length != 0 && $("#password").val().trim() != "" && $("#captcha").val().toLowerCase() == "") {
                $("#message").html("验证码不能为空！");
                $("#message").css("display", "block");
                return false;
            }
            if ($("#captcha").val().toLowerCase().length != 0 && $("#captcha").val().toLowerCase() != $("#canvasValue").val().toLowerCase()) {
                $("#message").html("验证码输入错误！");
                $("#message").css("display", "block");
                return false;
            }
            if ($("#password").val().trim() == "" && $("#name").val().trim() == "" && $("#captcha").val().toLowerCase() != $("#canvasValue").val().toLowerCase()) {
                $("#message").html("验证码输入错误！");
                $("#J-message p").html("会员名和密码不能为空！");
                $("#message").css("display", "block");
                $("#J-message").css("display", "block");
                return false;
            }
            if ($("#password").val().trim() != "" && $("#name").val().trim() == "" && $("#captcha").val().toLowerCase() != $("#canvasValue").val().toLowerCase()) {
                $("#message").html("验证码输入错误！");
                $("#J-message p").html("会员名不能为空！");
                $("#message").css("display", "block");
                $("#J-message").css("display", "block");
                return false;
            }
            if ($("#password").val().trim() == "" && $("#name").val().trim() != "" && $("#captcha").val().toLowerCase() != $("#canvasValue").val().toLowerCase()) {
                $("#message").html("验证码输入错误！");
                $("#J-message p").html("密码不能为空！");
                $("#message").css("display", "block");
                $("#J-message").css("display", "block");
                return false;
            }
            if ($("#password").val().trim() != "" && $("#name").val().trim() != "" && $("#captcha").val().toLowerCase() == $("#canvasValue").val().toLowerCase()) {
                $(".login-tip").css("display", "none");
                $("#message").css("display", "none");
                $("#J-message").css("display", "none");
                return true;
            }

        });
    });
</script>
<style>

    #picturecanvas {
        width: 100%;
        line-height: 20px;
        padding: 0;
        border-radius: 2px;
        position: relative;
        overflow: hidden;
        margin-top: 20px
    }

    #captcha {
        border: 2px solid #e5e5e5;
        width: 115px;
        padding-left: 15px;
        padding-right: 10px;
        height: 40px;
        line-height: 35px;
        font-size: 14px;
    }

    #canvas {
        float: right;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 5px;
        cursor: pointer;
        width: 110px;
        height: 40px;
    }

    #changeOne:hover {
        border-bottom: 1px blue solid;
    }

    #changeOne {
        display: inline-block;
        color: blue;
        height: 18px;
    }

    #message {
        width: 100%;
        color: red;
        height: 100%;
        display: none;
    }

</style>
<main class="login">
    <div class="login-content">
        <form method="post" action="loginIn" id="form">
            <div class="login-frame">
                <div class="login-tip">密码登录</div>
                <div class="login-msg error" id="J-message" style="display: none">
                    <p class="error"></p>
                </div>
                <div class="login-input">
                    <span class="login-input-icon">
                    <span class=" glyphicon glyphicon-user"></span>
                </span>
                    <input type="text" placeholder="会员名" name="username" id="name" autofocus autocomplete="off">
                </div>
                <div class="login-input">
        <span class="login-input-icon ">
                    <span class=" glyphicon glyphicon-lock"></span>
                </span>
                    <input type="password" placeholder="密码" name="password" id="password" autocomplete="off">
                  <%--  <input type="hidden" name="refer" id="refer" value="${refer}">--%>
                </div>

                <div id="picturecanvas">
                    <label style="font-size: medium">验证码&nbsp;&nbsp;&nbsp;</label>
                    <input id="captcha" type="text" autocomplete="off" placeholder="不区分大小写">
                    <canvas id="canvas"></canvas>
                    <div style="width: 100%;height: 20px;text-align: center">
                        <div style="width: 50%;height: 20px;float: left">
                            <label id="message"></label>
                        </div>
                        <div style="width: 50%;height: 20px;float: right;text-align: right">
                            <label id="changeOne">看不清？换一张</label>
                        </div>
                    </div>
                    <input id="canvasValue" type="hidden" name="canvasValue">
                </div>
                <div class="login-button">
                    <button id="btnRegister" type="submit" class="login-button">登 录</button>
                </div>
                <div class="login-bottom">
                    <a href="${pageContext.request.contextPath}/forgetPassword?resetType=2">忘记登录密码</a>
                    <a href="${pageContext.request.contextPath}/register">免费注册</a>
                </div>
            </div>
        </form>
    </div>
</main>