<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
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
    <title>LeaNote</title>

    <!-- <link media="all" type="text/css" rel="stylesheet" href="/css/bootstrap.min.css">
 -->
    <!-- <link media="all" type="text/css" rel="stylesheet" href="/css/bootstrap-theme.min.css">
 -->

    <link media="all" type="text/css" rel="stylesheet" href="/css/themes/paperwork-v1.min.css">

    <script src="/js/jquery-1.9.0.min.js"></script>
</head>
<body class="paperwork-guest">

<div class="container">
    <div class="guest-logo">
        <img class="guest-logo-img" src="/images/paperwork-logo.png">
    </div>

    <c:choose>
        <c:when test="${responseMsg.status eq 302}">
            <div class="alert alert-danger" role="alert">
                ${responseMsg.response}
            </div>
        </c:when>
        <c:when test="${responseMsg.status eq 200}">
            <div class="alert alert-danger" role="alert">
                    ${responseMsg.response}
            </div>
            <script>
                window.href="/page/login";
            </script>
        </c:when>
    </c:choose>

    <form method="POST"  onclick="return check()" action="/user/register" accept-charset="UTF-8" class="form-signin" role="form">
        <input name="_token" type="hidden" value="AvbovhxHMFQCAlR3GGQHC0YkPZcSGuX74vcdp6mc">
        <div class="alert alert-danger" id="loginMsg"></div>
        <h1 class="form-signin-heading">Register</h1>
        <div class="form-group ">
            <input class="form-control" id="email" placeholder="Email address" required="required" autofocus="autofocus" name="username" value="${user.username}" type="text">
        </div>
        <div class="form-group ">
            <input class="form-control" id="firstname" placeholder="First name" required="required" name="firstname" value="${user.firstname}" type="text">
        </div>
        <div class="form-group ">
            <input class="form-control" id="lastname" placeholder="Last name" required="required" name="lastname" value="${user.lastname}" type="text">
        </div>
        <div class="form-group ">
            <input class="form-control" id="pwd" placeholder="Password" required="required" name="password" type="password" value="">
        </div>
        <div class="form-group ">
            <input class="form-control" id="pwd2" placeholder="Confirm" required="required" name="password_confirmation" type="password" value="">
        </div>
        <div class="form-group ">
            <label for="ui_language" class="control-label">User interface language</label>
            <select id="ui_language" class="form-control" name="ui_language"><option value="ru">Russian</option><option value="en" selected="selected">English</option><option value="de">German</option><option value="zh-Hans">languages.zh-Hans</option><option value="nl">Dutch</option><option value="tr">Turkish</option><option value="zh_HK">languages.zh_HK</option><option value="pl">Polish</option><option value="cs">languages.cs</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="zh">languages.zh</option><option value="hu_HU">languages.hu_HU</option><option value="uk">Ukranian</option><option value="es_ES">languages.es_ES</option><option value="ach">languages.ach</option><option value="pt_BR">Portuguese (Brazil)</option><option value="sv">Swedish</option><option value="fr">French</option><option value="sp">Spanish</option><option value="hu">Hungarian</option><option value="zh-Hant">languages.zh-Hant</option><option value="chi-sim">Chinese (Simplified)</option></select>
        </div>
        <div class="checkbox">
            <input name="admin_creator" type="hidden" value="">
        </div>
        <div class="form-group">
            <input id="registerBtn" class="btn btn-lg btn-primary btn-block" type="submit" value="Sign up"/>
            <a class="btn btn-lg btn-default btn-block" href="/page/index">Back</a>
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

<script>
    $(function() {

    $("#email").focus();
    $("#loginMsg").hide();
    function hideMsg() {
        $("#loginMsg").hide();
    }


});
function showMsg(msg, id) {
        $("#loginMsg").html(msg).show();
        if(id) {
            $("#" + id).focus();
        }
};
function check()
{
        var email = $("#email").val();
        var pwd = $("#pwd").val();
        var pwd2 = $("#pwd2").val();
        var firstname=$("#firstname").val();
        var lastname=$("#lastname").val();
        if(!email) {
            showMsg("请输入Email", "email");
            return false;
        } else {
            var myreg = /^([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9\-_]+@([a-zA-Z0-9\-]+[_|\_|\.|\-]?)*[a-zA-Z0-9\-]+\.[0-9a-zA-Z]{2,6}$/;
            if(!myreg.test(email)) {
                showMsg("Email格式有误", "email");
                return false;
            }
        }
        if(!firstname)
        {
            showMsg("请输入姓", "firstname");
            return false;
        }
        else {
            if(firstname.length < 1||firstname.length>20) {
                showMsg("姓至少1位最多20位", "firstname");
                return false;
            }
        }

        if(!lastname)
        {
            showMsg("请输入名", "lastname");
            return false;
        }
        else {
            if(lastname.length < 1||lastname.length>20) {
                showMsg("名至少1位最多20位", "lastname");
                return false;
            }
        }

        if(!pwd) {
            showMsg("请输入密码", "pwd");
            return false;
        } else {
            if(pwd.length < 6) {
                showMsg("密码至少6位", "pwd");
                return false;
            }
        }
        if(!pwd2) {
            showMsg("请输入确认密码", "pwd2");
            return false;
        } else {
            if (pwd != pwd2) {
                showMsg("两次密码输入不正确", "pwd2");
                return false;
            }
        }
}
</script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="/js/ltie9compat.min.js"></script>

<![endif]-->
<!--[if lt IE 11]>
<script src="/js/ltie11compat.js"></script>

<![endif]-->
</body>
</html>
