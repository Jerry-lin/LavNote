<%--
  Created by IntelliJ IDEA.
  User: youyujie
  Date: 2018/6/8
  Time: 上午10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
    <meta charset="utf-8" />
    <title>
        Fastnote 个人中心
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
            <a href="/member/showMain.do?email={user.email}" class="navbar-brand clearfix" data-toggle="fullscreen">
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
            <li class="hi#" class="dk" target="_blank">
                <a href="#" class="dk" target="_blank">
                    我的博客
                </a>
            </li>
            <li class="hidden-xs">
                <a href="/user/logout.do?email=${user.email}" class="dk">
                    退出
                </a>
            </li>
            <li class="hidden-xs">
                <img alt="${user.email}" title="oeljeklaus2heart@gmail.com" src="/images/blog/default_avatar.png" id="myAvatar"/>
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
                                        <a href="/member">
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
                                                <a href="/member/showThirdAccount.do?email=${user.email}">
						<span>
							第三方帐户
						</span>
                                                </a>
                                            </li>

                                            <li>
                                                <a href="/member/showAvatar.do?email=${user.email}">
						<span>
							头像
						</span>
                                                </a>
                                            </li>




                                            <li>
                                                <a href="/member/showUserName.do?email=${user.email}">
						<span>
							用户名
						</span>
                                                </a>
                                            </li>

                                            <li>
                                                <a href="/member/showEmail.do?email=${user.email}">
						<span>
							Email
						</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="/member/showPasswd.do?email=${user.email}">
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
                        <a href="#" target="_blank">fastnote</a> © 2015-2016

                    </footer>
                </section>
            </aside>

            <section id="content">
                <section class="vbox">
                    <section class="scrollable padder">




                        <div class="m-b-md"> <h3 class="m-b-none">${user.email}, 欢迎来到fastnote.</h3></div>

                        <section class="panel panel-default">
                            <div class="row m-l-none m-r-none bg-light lter">
                                <div class="col-sm-6 col-md-3 padder-v b-r b-light">
				<span class="fa-stack fa-2x pull-left m-r-sm">
					<i class="fa fa-circle fa-stack-2x text-warning"></i>
					<i class="fa fa-file-o fa-stack-1x text-white"></i>
				</span>
                                    <a class="clear" href="javascript:;">
                                        <span class="h3 block m-t-xs"><strong>4</strong></span>
                                        <small class="text-muted text-uc">笔记</small>
                                    </a>
                                </div>

                                <div class="col-sm-6 col-md-3 padder-v b-r b-light">
				<span class="fa-stack fa-2x pull-left m-r-sm">
					<i class="fa fa-circle fa-stack-2x text-info"></i>
					<i class="fa fa-bold fa-stack-1x text-white"></i>
				</span>
                                    <a class="clear" href="javascript:;">
                                        <span class="h3 block m-t-xs"><strong>0</strong></span>
                                        <small class="text-muted text-uc">博客</small>
                                    </a>
                                </div>
                            </div>
                        </section>
                        <section class="panel panel-default">
                            <header class="panel-heading"><strong>帐户信息</strong></header>
                            <div class="list-group bg-white">
                                <div class="list-group-item">
                                    帐户类型:







                                    30天试用套餐
                                    试用时间还剩 <span class="label label-default">10</span> 天

                                    (<a target="_blank" href="https://leanote.com/pricing">了解试用过期后不能使用的服务</a>)







                                    <a class="btn btn-success" href="/pricing#buy">升级套餐</a>

                                </div>



                                <div class="list-group-item">
                                    优惠券升级帐户: <input id="key" type="text" class="form-control" style="display: inline-block; width: 300px;" placeholder="请输入优惠券"> <button id="keyBtn" class="btn btn-primary btn-sm">升级</button>
                                </div>
                                <div class="list-group-item">
                                    流量: 0.01MB/128MB (0.01%)
                                </div>
                            </div>
                        </section>




                        <section class="panel panel-default">
                            <div class="panel-heading">
                                <strong>Leanote动态</strong>
                            </div>
                            <ul class="list-group" id="eventsList"></ul>
                        </section>



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
<script>
    $(function() {

        var url = "https://leanote.com/api/note/listCateLatest?notebookId=5446753cfacfaa4f56000000";
        function renderItem(item) {
            return '<li class="list-group-item"><p><a target="_blank" href="http://leanote.com/blog/post/' + item.NoteId + '">' + item.Title + '</a></p><small class="block text-muted"><i class="fa fa-clock-o"></i> ' + goNowToDatetime(item.PublicTime) + '</small></li>';
        }
        $.ajax({
            dataType: "jsonp",
            url: url,
            type: "GET",
            jsonp: "callback",
            jsonpCallback: "jsonpCallback",
            success: function(data) {
                if(typeof data == "object" && data.Ok) {
                    var list = data.List;
                    var html = "";
                    for(var i = 0; i < list.length; ++i) {
                        var item = list[i];
                        html += renderItem(item);
                    }
                    $("#eventsList").html(html);
                }
            }
        });

        $("#keyBtn").click(function () {
            var val = $.trim($('#key').val());
            if (!val) {
                $('#key').focus();
                return;
            }
            var me = $(this);
            me.button('loading');
            $.post('/user/upgradeAccountByKey', {key: val}, function (ret) {
                me.button('reset');
                if (ret.Ok) {
                    alert('升级成功, ' + (ret.Item.AccountType == 'pro' ? '高级账户' : '旗舰账户' ) + ' : ' + ret.Item.Month + ' 月');
                    location.reload();
                }
                else {
                    alert(ret.Msg || '升级失败');
                }
            });
        });
    });

</script>

</body>
</html>
