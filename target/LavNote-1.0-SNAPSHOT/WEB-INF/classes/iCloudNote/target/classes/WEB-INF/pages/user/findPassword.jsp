<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="keywords" content="fastnote,快笔记,fastnote.com">
    <meta name="description" content="fastnote, 快笔记，有极客范的云笔记！">
    <meta name="author" content="fastnote,快笔记">
    <title>找回密码</title>

    <link href="/css/bootstrap-min.css" rel="stylesheet">
    <link href="/css/font-awesome-4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/css/index.css?t=a" rel="stylesheet">
    <style>
        html, body {
            height: 100%;
        }
    </style>
</head>
<body id="boxBody" class="lang-zh-cn">

<section id="box" class="animated fadeInUp">
    <div>
        <h1 id="logo"></h1>
        <div id="boxForm">
            <div id="boxHeader">找回密码</div>
            <form>
                <div class="alert alert-danger" id="loginMsg"></div>

                <div class="form-group">
                    <label class="control-label" for="email">Email</label>
                    <input type="text" class="form-control" id="email" name="email" value="">
                </div>

                <button id="loginBtn" class="btn btn-success">找回密码</button>
            </form>
        </div>

    </div>
</section>

<div id="boxFooter">
    <p>
        <a href="/page/login.do">登录</a>
        &nbsp;
        <a href="/page/index.do">主页</a>
    </p>
    <p>
        © <a href="/page/index.do">fastnote</a>
    </p>
</div>

<script src="/js/jquery-1.9.0.min.js"></script>
<script src="/js/bootstrap.js"></script>
<script src="/js/common.js"></script>

<script>
    $(function() {
        $("#email").focus();
        if($("#email").val()) {
            $("#pwd").focus();
        }
        function showMsg(msg, id) {
            $("#loginMsg").html(msg).show();
            if(id) {
                $("#" + id).focus();
            }
        }
        function hideMsg() {
            $("#loginMsg").hide();
        }
        $("#loginBtn").click(function(e){
            e.preventDefault();
            var email = $("#email").val();
            if(!email) {
                showMsg("请输入Email", "email");
                return;
            } else {
                var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9\-]+\.[a-zA-Z]{2,10}$/;
                if(!myreg.test(email)) {
                    showMsg("Email格式有误", "email");
                    return;
                }
            }

            $("#loginBtn").html("正在处理...").addClass("disabled");


            $.post("/user/doFindPassword.do", {email: email}, function(e) {
                $("#loginBtn").html("找回密码").removeClass("disabled");
                if(e.ok) {
                    var msg = "已经将修改密码的链接发送到您的邮箱, 请查收邮件.";
                    var loginAddress = getEmailLoginAddress(email);
                    if(loginAddress) {
                        msg += ' <a target="_blank" href="' + loginAddress + '">查收邮箱</a>';
                    }
                    $("#loginMsg").html(msg).show().removeClass("alert-danger").addClass("alert-success");
                } else {
                    showMsg(e.Msg, "email");
                }
            });
        });
    });
</script>
</body>
</html>