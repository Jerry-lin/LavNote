<%--
  Created by IntelliJ IDEA.
  User: youyujie
  Date: 2018/6/11
  Time: 上午10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Paperwork (http://paperwork.rocks)">

    <meta name="theme-color" content="#03a9f4">
    <meta name="apple-mobile-web-app-status-bar-style" content="#03a9f4">
    <!-- <link rel="apple-touch-startup-image" href=""> -->

    <link rel="apple-touch-icon" sizes="60x60" href="/images/paperwork-icons/favicon-60x60.png">
    <link rel="apple-touch-icon" sizes="76x76" href="/images/paperwork-icons/favicon-76x76.png">
    <link rel="apple-touch-icon" sizes="128x128" href="/images/paperwork-icons/favicon-128x128.png">

    <link rel="apple-touch-icon" sizes="152x152" href="/images/paperwork-icons/favicon-152x152.png">


    <link rel="manifest" href="/manifests/homescreen-manifest.json">
    <title>LavNote</title>

    <!-- <link media="all" type="text/css" rel="stylesheet" href="/css/bootstrap.min.css">
 -->
    <!-- <link media="all" type="text/css" rel="stylesheet" href="/css/bootstrap-theme.min.css">
 -->

    <link media="all" type="text/css" rel="stylesheet" href="/css/themes/paperwork-v1.min.css">


</head>
<body class="paperwork-guest">

<div class="container">
    <div class="guest-logo">
        <img class="guest-logo-img" src="/images/paperwork-logo.png">
    </div>


    <form method="POST" action="/user/login" accept-charset="UTF-8" class="form-signin" role="form"><input name="_token" type="hidden" value="AvbovhxHMFQCAlR3GGQHC0YkPZcSGuX74vcdp6mc">
        <h1 class="form-signin-heading">Sign in</h1>
        <div class="form-group ">
            <input class="form-control" placeholder="Email address" required="required" autofocus="autofocus" name="username" type="text" value="">
        </div>
        <div class="form-group ">
            <input class="form-control" placeholder="Password" required="required" name="password" type="password" value="">
        </div>
        <div class="checkbox">
            <label>
                <input name="remember_me" type="checkbox" value="1"> Remember me
            </label>
        </div>
        <div class="checkbox">
            <a href="/user/forgot"><i class="fa fa-life-saver"></i> Forgot your password?</a>
        </div>
        <div class="form-group">
            <input class="btn btn-lg btn-primary btn-block" type="submit" value="Sign in">
        </div>
        <div class="">
            <div class="form-group">
                <a class="btn btn-lg btn-default btn-block" href="/page/register">Sign up</a>
            </div>
        </div>

    </form>
    <div class="footer" ondblclick="this.style.display = 'none'">
        <div class="error-reporting">
            <div class="alert alert-warning" role="alert">
                <p>Found a bug? LavNote cannot connect to Github to check the latest version. A solution to this can be installling the curl Java extension. This is not mandatory, however before reporting any issues, please make sure that you are using the latest version.  </p>
                <p>Double click to dismiss.
            </div>
        </div>
    </div>
</div> <!-- /container -->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="/js/ltie9compat.min.js"></script>

<![endif]-->
<!--[if lt IE 11]>
<script src="/js/ltie11compat.js"></script>

<![endif]-->
</body>
</html>
