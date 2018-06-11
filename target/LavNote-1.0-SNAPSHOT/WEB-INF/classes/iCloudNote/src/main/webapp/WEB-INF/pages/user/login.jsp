<%--
  Created by IntelliJ IDEA.
  User: youyujie
  Date: 2018/5/15
  Time: 下午7:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="keywords" content="leanote,快笔记,leanote.com">
    <meta name="description" content="leanote, 快笔记，快人一步的云笔记！">
    <meta name="author" content="leanote,快笔记">
    <title>登录</title>

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


<script type="text/x-jsrender" id="tCaptcha">
	<div class="form-group">
		<label class="control-label">"验证码"</label>
	    <input type="text" class="form-control" id="captcha" name="captcha">
	    <a id="reloadCaptcha" title="刷新验证码" onclick="$('#captchaImage').attr('src', '/captcha/get?' + ((new Date()).getTime()))"><img src="/captcha/get" id="captchaImage"/></a>
	</div>
</script>
<section id="box"  class="animated fadeInUp">

    <div>
        <h1 id="logo"></h1>
        <div id="boxForm">
            <div id="boxHeader">登录</div>
            <form method="post">
                <div class="alert alert-danger" id="loginMsg"></div>
                <input id="from" type="hidden" value="/note/showMain.do"/>
                <div class="form-group">
                    <label class="control-label">用户名或Email</label>
                    <input type="text" class="form-control" id="email" name="email" value="${email}">
                </div>
                <div class="form-group">
                    <label class="control-label">密码</label>
                    <input type="password" class="form-control" id="pwd" name="pwd">
                </div>

                <div id="captchaContainer">

                </div>

                <div class="clearfix">
                    <a href="/page/findPassword.do" class="pull-right m-t-xs"><small>忘记密码?</small></a>
                    <button id="loginBtn" class="btn btn-success">登录</button>
                </div>
                <div class="line line-dashed"></div>

                <p class="text-muted text-center"><small>使用第三方帐号登录</small></p>


                <div class="third-account">
                    <a href="#" id="wechat" class="btn btn-wechat m-b-sm btn-third">
                        <i class="fa fa-wechat pull-left"></i> 微信
                    </a>
                    <a href="#" id="qq" class="btn btn-qq m-b-sm btn-third">
                        <i class="fa fa-qq pull-left"></i> QQ
                    </a>

                    <a href="#" id="weibo" class="btn btn-weibo m-b-sm btn-third">
                        <i class="fa fa-weibo pull-left"></i> 微博
                    </a>

                    <a href="#" id="github" class="btn btn-github m-b-sm btn-third">
                        <i class="fa fa-github pull-left"></i> Github
                    </a>
                    <div class="third-split"></div>
                    <a href="#" id="google" class="btn btn-google m-b-sm btn-third">
                        <i class="fa fa-google pull-left"></i> Google
                    </a>
                    <a href="#" id="facebook" class="btn btn-facebook m-b-sm btn-third">
                        <i class="fa fa-facebook pull-left"></i> Facebook
                    </a>
                    <a href="#" id="twitter" class="btn btn-twitter m-b-sm btn-third">
                        <i class="fa fa-twitter pull-left"></i> Twitter
                    </a>
                </div>


                <div class="line line-dashed"></div>

                <p class="text-muted text-center"><small>还无帐户?</small></p>


                <a href="/page/register.do" class="btn btn-default btn-block">注册</a>




            </form>
        </div>
    </div>
</section>

<div id="boxFooter">
    <p>
        <a href="/page/index.do">主页</a>
    </p>
    <p>
        © <a href="/page/index.do">Leanote</a>
    </p>
</div>

<script src="/js/jquery-1.9.0.min.js"></script>
<script src="/js/bootstrap.js"></script>

<script>
    $(function() {

        if (navigator.userAgent.toLowerCase().indexOf('micromessenger') >= 0) {
            location.href = '/mp/note?from=' + ($("#from").val() || '');
            return;
        }

        var needCaptcha = "";

        if(needCaptcha){
            $("#captchaContainer").html($("#tCaptcha").html());
        }

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
            var pwd = $("#pwd").val();
            var captcha = $("#captcha").val()
            if(!email) {
                showMsg("请输入用户名", "email");
                return;
            }
            if(!pwd) {
                showMsg("请输入密码", "pwd");
                return;
            } else {
                if(pwd.length < 6) {
                    showMsg("密码有误", "pwd");
                    return;
                }
            }
            if(needCaptcha && !captcha) {
                showMsg("请输入验证码", "captcha");
                return;
            }

            $("#loginBtn").html("正在登录...").addClass("disabled");



            $.post("/user/doLogin.do", {email: email, pwd: pwd, captcha: $("#captcha").val()}, function(e) {
                $("#loginBtn").html("登录").removeClass("disabled");
                if(e.ok) {
                    $("#loginBtn").html("登录成功, 正在跳转...");
                    var from ="/note/showMain.do?mail="+e.msg;
                    location.href = from;
                } else {
                    if(e.Item && $.trim($("#captchaContainer").text()) == "") {
                        $("#captchaContainer").html($("#tCaptcha").html());
                        needCaptcha = true
                    }
                    showMsg(e.msg);
                }
            });
        });


        $(".btn-third").click(function(e) {
            e.preventDefault();
            $(this).button("loading");
            var id = $(this).attr('id');
            location.href = '/oauth/url?t=' + id;
        });
    });
</script>
</body>
</html>
