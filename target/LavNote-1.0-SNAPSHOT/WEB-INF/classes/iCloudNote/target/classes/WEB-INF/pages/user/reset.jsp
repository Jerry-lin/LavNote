<%--
  Created by IntelliJ IDEA.
  User: youyujie
  Date: 2018/5/19
  Time: 下午3:59
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
    <meta name="keywords" content="fastnote,快笔记,fastnote.com">
    <meta name="description" content="fastnote, 快笔记，快人一步的云笔记！">
    <meta name="author" content="fastnote,快笔记">
    <title>修改密码</title>

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
            <div id="boxHeader">修改密码</div>
            <form>
                <div class="alert alert-danger" id="loginMsg"> </div>

                <div class="form-group">
                    <label class="control-label" for="token">token</label>

                    <br />
                    ${token}
                </div>

                <div class="form-group">
                    <label class="control-label" for="pwd">密码</label>
                    <input type="password" class="form-control" id="pwd" name="pwd">
                    密码至少6位
                </div>
                <div class="form-group">
                    <label class="control-label" for="pwd2">确认密码</label>
                    <input type="password" class="form-control" id="pwd2" name="pwd2" >
                </div>
                <button id="loginBtn" class="btn btn-success">修改密码</button>
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
        © <a href="/page/index.do">Fastnote</a>
    </p>
</div>

<script src="/js/jquery-1.9.0.min.js"></script>
<script src="/js/bootstrap.js"></script>

<script>

    $(function() {
        $("#pwd").focus();
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

            var pwd = $("#pwd").val();
            var pwd2 = $("#pwd2").val();

            if(!pwd) {
                showMsg("请输入密码", "pwd");
                return;
            } else {
                if(pwd.length < 6) {
                    showMsg("密码至少6位", "pwd");
                    return;
                }
            }
            if(!pwd2) {
                showMsg("请输入确认密码", "pwd2");
                return;
            } else {
                if(pwd != pwd2) {
                    showMsg("两次密码输入不正确", "pwd2");
                    return;
                }
            }

            $("#loginBtn").html("正在处理...").addClass("disabled");


            $.post("/user/findPasswordUpdate.do", {pwd: pwd,token:"${token}"}, function(e) {
                $("#loginBtn").html("修改密码").removeClass("disabled");
                if(e.ok) {
                    $("#loginBtn").html("修改成功, 正在跳转到登录页");
                    location.href = "/page/login.do?email=${email}";
                } else {
                    showMsg(e.Msg, "pwd");
                }
            });
        });
    });
</script>
</body>
</html>
