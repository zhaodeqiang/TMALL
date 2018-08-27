<%--
  Created by IntelliJ IDEA.
  User: ZDQ
  Date: 2018/5/29
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>忘记密码</title>
<link rel="stylesheet" href="css/toast.css">
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/toast.js"></script>
<!-- 必须先引用JQuery -->
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<style>
    body {
        margin-top: 20px;
        margin: 0 auto;
    }

    .carousel-inner .item img {
        width: 100%;
        height: 300px;
    }

    font {
        color: #3164af;
        font-size: 18px;
        font-weight: normal;
        padding: 0 10px;
    }

    #canvas {
        float: right;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 5px;
        cursor: pointer;
        width: 110px;
        height: 33px;
    }

    #reset {
        margin-left: 160px;
        height: 35px;
        width: 130px;
        background-color: red;
        border-radius: 2px;
        color: white;
        border: none;
        text-align: center;
        font-size: medium;
    }

    #changeOne:hover {
        border-bottom: 1px blue solid;
    }

    .error {
        color: red; /*  表单校验插件字体默认黑色，改为红色*/
    }
</style>

<script type="text/javascript">

    // 手机号码验证
    $.validator.addMethod("isMobile", function (value, element) {
        var length = value.length;
        var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));//插件中的方法
    });//this.optional(element) 被校验对象为空时值为true，可选的；不为空时值为false

    //自定义校验规则
    $.validator.addMethod(
        //规则名称
        "isExist",
        //校验函数
        function (value) {
            //value:输入的内容
            //element:被校验的元素对象
            //params：规则对应的参数值
            //目的：对输入的username进行ajax校验
            var flag = false;
            $.ajax({
                async: false, //不能用异步  ajax默认都是异步  //不配置匿名访问会被shiro拦截
                url: "${pageContext.request.contextPath}/checkUsername",
                data: {
                    username: value
                },
                type: "post",
                dataType: "json",
                success: function (data) {
                    flag = data.isExist;
                }
            });
            //返回false表示该校验器不通过
            return flag;
        });

    $(function () { //注意逗号隔开，一个地方错误校验功能就不成功！！！！！
        var msg = "${fail}";
        if (msg.length !== 0) {
            $('body').toast({
                content: msg,
                duration: 1000,
                isCenter: true,
                background: 'rgba(230,0,0,0.5)',
                animateIn: 'bounceIn-hastrans',
                animateOut: 'bounceOut-hastrans',
            });
        }
        $("#myform").validate({
            rules: {
                "username": {
                    "required": true,
                    "isExist": true
                },
                "password": {
                    "required": true,
                    "rangelength": [6, 12]
                },
                "repassword": {
                    "required": true,
                    "rangelength": [6, 12],
                    "equalTo": "#password"
                },
                "email": {
                    "required": true,
                    "email": true
                },
                "phone": {
                    "required": true,
                    "minlength": 11,
                    // 自定义方法：校验手机号在数据库中是否存在
                    // checkPhoneExist : true,
                    "isMobile": true
                },
                "checkCode": {
                    "required": true,
                    "isRight": true
                }
            },
            messages: {
                "username": {
                    "required": "会员名不能为空!",
                    "isExist": "该会员名不存在!"
                },
                "password": {
                    "required": "密码不能为空!",
                    "rangelength": "密码长度6-12位!"
                },
                "repassword": {
                    "required": "确认密码不能为空!",
                    "rangelength": "密码长度6-12位!",
                    "equalTo": "两次密码不一致!"
                },
                "email": {
                    "required": "邮箱不能为空!",
                    "email": "邮箱格式不正确!"
                },
                "phone": {
                    "required": "请输入手机号!",
                    "minlength": "手机号不能少于于11个数字!",
                    "isMobile": "请正确填写您的手机号码!"
                },
                "checkCode": {
                    "required": "验证码不能为空!",
                    "isRight": "验证码不正确!"
                }
            }
        });
    });

</script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8"
             style="background: #fff; padding: 40px 30px; margin: 30px; border: 7px solid #ccc;">
            <font style="margin-left: 110px">忘记密码</font>FORGET PASSWORD
            <form id="myform" class="form-horizontal"
                  action="${pageContext.request.contextPath }/resetPassword" method="post"
                  style="margin-top: 5px;">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label" style="margin-left: 80px">会员名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="username"
                               name="username" placeholder="请输入您的会员名" autocomplete="off">
                        <input type="hidden" value="forget" name="resetType">
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone" class="col-sm-2 control-label" style="margin-left: 80px">手机号</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="phone"
                               name="phone" placeholder="请输入您注册的手机号" autocomplete="off">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="margin-left: 80px">新密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="password"
                               name="password"
                               placeholder="请输入新密码" autocomplete="off">
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirmpwd" class="col-sm-2 control-label" style="margin-left: 80px">确认密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="confirmpwd"
                               name="repassword" placeholder="请输入确认密码" autocomplete="off">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label" style="margin-left: 80px">Email</label>
                    <div class="col-sm-6">
                        <input type="email" class="form-control" id="inputEmail3"
                               name="email" placeholder="请输入您注册的Email" autocomplete="off">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label" style="margin-left: 80px">验证码</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="checkCode" id="checkCode" autocomplete="off"
                               placeholder="不区分大小写">
                    </div>
                    <div class="col-sm-2">
                        <canvas id="canvas"></canvas>
                        <label id="changeOne" style="display: inline-block;color: blue;height: 18px">看不清？换一张</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-4">
                        <button type="submit" name="submit" id="reset">
                            重置密码
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>

</body>
</html>





