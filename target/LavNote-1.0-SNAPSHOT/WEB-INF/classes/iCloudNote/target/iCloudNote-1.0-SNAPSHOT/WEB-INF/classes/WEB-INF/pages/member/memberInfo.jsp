<%--
  Created by IntelliJ IDEA.
  User: youyujie
  Date: 2018/6/8
  Time: 上午10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>s
<!DOCTYPE html>
<html lang="en" class="app">
<head>
    <meta charset="utf-8" />
    <title>
        头像
    </title>
    <meta name="description" content="leanote member center"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link href="/css/font-awesome-4.2.0/css/font-awesome.css" rel="stylesheet">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/member/css/member.css?i=2" rel="stylesheet">

</head>
<body class="lang-zh-cn">
<section class="vbox">
    <header class="bg-dark dk header navbar navbar-fixed-top-xs">
        <div class="navbar-header logo-ctn">
            <a href="/member/showMains.do?email={user.email}" class="navbar-brand clearfix" data-toggle="fullscreen">
                <span id="logo"></span>
                <span class="logo-title">个人中心</span>
            </a>
        </div>

        <ul class="nav navbar-nav navbar-right m-n hidden-xs nav-user">
            <li class="hidden-xs">
                <a href="#" class="dk" >
                    云协作
                </a>
            </li>
            <li class="hidden-xs">
                <a href="#" class="dk">
                    探索
                </a>
            </li>
            <li class="hidden-xs">
                <a href="/note" class="dk">
                    我的笔记
                </a>
            </li>
            <li class="hidden-xs">
                <a href="#" class="dk" target="_blank">
                    我的博客
                </a>
            </li>
            <li class="hidden-xs">
                <a href="/user/logout.do?email={user.email}" class="dk">
                    退出
                </a>
            </li>
            <li class="hidden-xs">
                <img alt="${user.email}" title="${user.email}" src="/images/blog/default_avatar.png" id="myAvatar"/>
            </li>
        </ul>
    </header>
    <section>
        <section class="hbox stretch">

            <aside class="bg-light lter b-r aside-md hidden-print" id="nav">
                <section class="vbox">


                    <section class="w-f scrollable">
                        <div class="slim-scroll" data-height="auto" data-disable-fade-out="true"
                             data-distance="0" data-size="5px" data-color="#333333">

                            <nav class="nav-primary">
                                <ul class="nav" id="nav">

                                    <li class="active">
                                        <a href="/member/showMain.do?email={user.email}">
                                            <i class="fa fa-dashboard icon">
                                                <b class="bg-success">
                                                </b>
                                            </i>
                                            <span>
					个人中心
				</span>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="#">
                                            <i class="fa fa-user icon">
                                                <b class="bg-danger">
                                                </b>
                                            </i>
                                            <span class="pull-right">
					<i class="fa fa-angle-down text">
					</i>
					<i class="fa fa-angle-up text-active">
					</i>
				</span>
                                            <span>
					帐户信息
				</span>
                                        </a>

                                        <ul class="nav lt">
                                            <li>
                                                <a href="/member/user/thirdAccount">
						<span>
							第三方帐户
						</span>
                                                </a>
                                            </li>

                                            <li>
                                                <a href="/member/user/avatar">
						<span>
							头像
						</span>
                                                </a>
                                            </li>




                                            <li>
                                                <a href="/member/user/username">
						<span>
							用户名
						</span>
                                                </a>
                                            </li>

                                            <li>
                                                <a href="/member/user/email">
						<span>
							Email
						</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="/member/user/password">
						<span>
							密码
						</span>
                                                </a>
                                            </li>

                                        </ul>
                                    </li>

                                    <li id="adminEmailNav">
                                        <a href="#layout">
                                            <i class="fa fa-bold icon">
                                                <b class="bg-warning">
                                                </b>
                                            </i>
                                            <span class="pull-right">
					<i class="fa fa-angle-down text">
					</i>
					<i class="fa fa-angle-up text-active">
					</i>
				</span>
                                            <span>
					博客
				</span>
                                        </a>
                                        <ul class="nav lt">
                                            <li>
                                                <a href="/member/blog/index">
						<span>
							博文管理
						</span>
                                                </a>
                                            </li>

                                            <li>
                                                <a href="/member/blog/base">
						<span>
							博客基本设置
						</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="/member/blog/comment">
						<span>
							评论设置
						</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="/member/blog/domain">
						<span>
							域名设置
						</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="/member/blog/cate">
						<span>
							分类管理
						</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="/member/blog/single">
						<span>
							单页管理
						</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="/member/blog/paging">
						<span>
							分页与排序设置
						</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="/member/blog/theme">
						<span>
							主题
						</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>

                                    <li class="active">
                                        <a href="/member/group/index">
                                            <i class="fa fa-users icon">
                                                <b class="bg-success">
                                                </b>
                                            </i>
                                            <span>
					用户组
				</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>

                        </div>
                    </section>
                    <footer class="footer lt hidden-xs b-t b-light" style="min-height: initial;
padding: 10px 15px;text-align:center;">
                        <a href="#" target="_blank">leanote</a> © 2015-2016

                    </footer>
                </section>
            </aside>

            <section id="content">
                <section class="vbox">
                    <section class="scrollable padder">





                        <div class="m-b-md"> <h3 class="m-b-none">头像</h3></div>

                        <div class="row">

                            <div class="col-sm-8">
                                <form id="uploadAvatar" method="post" action="/file/uploadAvatar" enctype="multipart/form-data">
                                    <section class="panel panel-default">
                                        <div class="panel-body">
                                            <div class="form-group">
                                                <div id="dropAvatar" class="dropzone">
                                                    <div>
                                                        <img src="/images/blog/default_avatar.png" id="avatar"/>
                                                    </div>
                                                    <a class="btn btn-success btn-choose-file">
                                                        <span class="fa fa-upload"></span> 选择图片
                                                    </a>
                                                    <input type="file" name="file" multiple/>
                                                </div>
                                                <div id="avatarUploadMsg">
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                </form>
                            </div>

                        </div>

                        </div>
                    </section>
                </section>
            </section>
        </section>
    </section>
</section>

<script src="/js/jquery-1.9.0.min.js"></script>
<script src="/js/bootstrap.js"></script>
<script src="/js/i18n/msg.zh-cn.js?m=2"></script>
<script src="/admin/js/artDialog/jquery.artDialog.js?skin=default"></script>
<script src="/js/common.js"></script>
<script src="/member/js/member.js"></script>
<script>
    $(function() {
        var pathname = location.pathname;
        var search = location.search;
        var fullPath = pathname;
        if(search.indexOf("?t=") >= 0) {
            var fullPath = pathname + search;
        }

        if(fullPath == "/member/index") {
            fullPath = "/member";
        } else if (fullPath == '/member/blog/updateTheme') {
            fullPath = '/member/blog/theme';
        }

        $("#nav > li").removeClass("active");

        var $thisLi = $('#nav a[href="' + fullPath + '"]').parent();
        $thisLi.addClass("active");

        $thisLi.parent().parent().addClass('active');
    });
</script>
<script src="/js/require.js"></script>
<script>
    var UrlPrefix = "https://localhosts:8080";
    var GlobalConfigs = {"uploadAttachSize":0,"uploadAvatarSize":0,"uploadBlogLogoSize":0,"uploadImageSize":0};
    require.config({
        baseUrl: '/public',
        paths: {
            'avatar': 'member/js/avatar',
            'fileupload': 'js/plugins/libs-min/fileupload',
        }
    });
    $(function() {
        require(['avatar'], function(avatar) {});
    });
</script>


</body>
</html>

