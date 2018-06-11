<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="keywords" content="fastnote,快笔记,fastnote.com">
    <meta name="description" content="fastnote, 快笔记，快人一步的云笔记！">
    <meta name="author" content="fastnote,快笔记">
    <%@ page isELIgnored="false" %>
    <title>验证邮箱</title>

    <link href="/css/bootstrap-min.css" rel="stylesheet">
    <link href="/css/font-awesome-4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="/css/index.css?t=a" rel="stylesheet">
    <style>
        html, body {
            height: 100%;
        }
    </style>
</head>
<body id="boxBody" class="lang-">

<section id="box" class="animated fadeInUp">
    <div>
        <h1 id="logo">fastnote</h1>
        <div id="boxForm">
            <div id="boxHeader">${requestScope.title}</div>
            <form>
                <div class="alert alert-danger" id="loginMsg"> </div>
                ${requestScope.status}

                <br />
                ${requestScope.message}

                <br />
                <a href="/page/login.do">回到我的笔记</a>
            </form>
        </div>
    </div>
</section>

<div id="boxFooter">
    <p>
        <a href="/page/index.do">主页</a>
    </p>
    <p>
        <a href="/page/index.do">fastnote</a> © 2014
    </p>
</div>
</body>
</html>