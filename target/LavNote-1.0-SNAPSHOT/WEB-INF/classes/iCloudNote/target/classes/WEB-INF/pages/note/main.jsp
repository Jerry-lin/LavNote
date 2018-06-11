<%--
  Created by IntelliJ IDEA.
  User: youyujie
  Date: 2018/5/16
  Time: 上午10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="keywords" content="fastote,快笔记,fastnote.com">
    <meta name="description" content="Fastnote,快笔记,快笔记，有极客范的云笔记！">
    <title>Fastnote, 快笔记，快人一步的云笔!</title>
    <link href="/css/bootstrap-min.css" rel="stylesheet" />

    <link rel="stylesheet" href="/css/tinymce/skin.min.css" rel="stylesheet"/>

    <link href="/css/font-awesome-4.2.0/css/font-awesome-min.css" rel="stylesheet" />
    <link href="/css/zTreeStyle/zTreeStyle-min.css" rel="stylesheet" />

    <link href="/md/themes/default.css?t=o2" rel="stylesheet" />

    <link rel="stylesheet" href="/js/contextmenu/css/contextmenu-min.css?id=1" type="text/css" />
    <script>
        var hash = location.hash;
        if(hash.indexOf("writing") >= 0) {
            var files = '<link rel="stylesheet" href="/css/theme/writting-overwrite.css?t=r5" type="text/css" id="themeLink" />';
        } else {
            var files ='<link rel="stylesheet" href="/css/theme/default.css?t=r10" type="text/css" id="themeLink" />';
        }
        document.write(files);
    </script>

    <script>
        function log(o) {

        }
    </script>
</head>
<body class="leanote-plugin lang-zh-cn">
<div id="page">
    <div id="pageInner">

        <div id="header">
            <a id="logo" href="/page/index.do" title="Fastnote 主页" class="pull-left"></a>
            <div id="switcher" class="pull-left">
                <i class="fa fa-dedent" id="leftSwitcher" title="隐藏左侧"></i>
                <span id="leftSwitcher2" class="fa fa-indent" title="展开左侧"></span>
            </div>

            <div class="pull-left" id="searchWrap">
                <form class="navbar-form form-inline col-lg-2" id="searchNote">
                    <input class="form-control" placeholder="搜索笔记" type="text" id="searchNoteInput">
                </form>
            </div>

            <div class="pull-left" style="" id="newNoteWrap">

                <div id="newMyNote">
                    <a class="new-note" id="newNoteBtn" title="新建普通笔记">

                        <span class="new-note-text">新建普通笔记</span>
                        <span class="new-note-text-abbr">新建</span>
                    </a>
                    <span class="new-split">|</span>
                    <a class="new-note new-markdown" id="newNoteMarkdownBtn" title="新建Markdown笔记">

                        <span class="new-markdown-text">新建Markdown笔记</span>
                        <span class="new-markdown-text-abbr">Md</span>
                    </a>
                    <span class="for-split"> - </span>
                    <span id="curNotebookForNewNote" notebookId=""></span>

                </div>


                <div id="newSharedNote" style="display: none">
                    <a id="newSharedNoteBtn" class="new-note">

                        <span class="new-note-text">新建普通笔记</span>
                        <span class="new-note-text-abbr">新建</span>
                    </a>
                    <span class="new-split">|</span>
                    <a id="newShareNoteMarkdownBtn" class="new-note new-markdown" title="新建Markdown笔记">
                        <span class="new-markdown-text">新建Markdown笔记</span>
                        <span class="new-markdown-text-abbr">Md</span>
                    </a>
                    <span class="for-split"> - </span>
                    <span id="curNotebookForNewSharedNote" notebookId="" userId=""></span>

                </div>
            </div>

            <div class="pull-left" id="leanoteMsg">
                <span id="msg" style="display: inline-block;"></span>
                <span id="loading">
					</span>
            </div>

            <div class="pull-right" id="myProfile">
                <div class="dropdown">
                    <a class="dropdown-toggle profile-img" title="${user.email}" data-toggle="dropdown">
                        <img alt="${user.email}" title="${user.email}" src="/images/blog/default_avatar.png" id="myAvatar"/>
                        <span class="username">
								${user.email}
							</span>
                        <i class="fa fa-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu li-a" role="menu">
                        <li role="presentation" id="setTheme">
                            <a>
                                <i class="fa fa-sun-o"></i>
                                主题
                            </a>
                        </li>
                        <li role="presentation" class="divider"></li>
                        <li role="presentation" class="mobile-show">
                            <a href="#"><i class="fa fa-cubes"></i> 云协作</a>
                        </li>
                        <li role="presentation" class="divider mobile-show"></li>
                        <li role="presentation">
                            <a target="_blank" href="#">
                                <i class="fa fa-globe"></i>
                                我的博客</a>
                        </li>
                        <li role="presentation" class="divider"></li>
                        <li role="presentation">
                            <a href="/member/showMain.do?email=${user.email}" target="_blank">
                                <i class="fa fa-user"></i>
                                个人中心
                            </a>
                        </li>

                        <li role="presentation">
                            <a href="/member/blog/index" target="_blank">
                                <i class="fa fa-bold"></i>
                                博客设置
                            </a>
                        </li>

                        <li role="presentation" class="divider my-link writing-mobile-hide"></li>
                        <li role="presentation" class="my-link toggle-editor-mode writing-mobile-hide" >
                            <a href="#writing" title="写作模式 ctrl/cmd+e"><i class="fa fa-rocket"></i>
                                写作模式</a>
                        </li>


                        <li role="presentation" class="divider"></li>
                        <li role="presentation" onclick=logout("${user.email}")>
                            <i class="fa fa-sign-out"></i>
                            退出
                        </li>
                    </ul>
                </div>
            </div>


            <div class="pull-right top-nav writting-hide mobile-hide">
                <a id="upgradeAccount" href="/pricing" target="_blank">
                    升级帐户
                    <span class="dot"></span>
                </a>
            </div>

            <div class="pull-right top-nav writting-hide lea-blog">
                <a target="_blank" href="#">
                    探索
                </a>
            </div>
            <div class="pull-right top-nav writting-hide lea-blog">
                <a target="_blank" href="#">
                    云协作
                </a>
            </div>
            <div class="pull-right toggle-editor-mode top-nav" id="toggleEditorMode">
                <a href="#writing"><span>写作模式</span></a>
            </div>



            <div style="clear: both"></div>
        </div>
        <div id="mainContainer" class="clearfix">
            <div id="mainMask">
                <img src="/images/loading-30.gif" />
                <br />
                <br />
                loading...
            </div>
            <div id="leftNotebook">
                <div id="notebook">
                    <div class="folderNote opened" id="myNotebooks">
                        <div class="folderHeader clearfix">
                            <i class="fa fa-book pull-left fa-left"></i>
                            <span class="pull-left">
									笔记本
								</span>
                            <div class="pull-right" id="addNotebookPlus" title="添加笔记本">
                                <i class="fa  fa-plus" title="添加笔记本"></i>
                            </div>
                        </div>

                        <div class="folderBody">
                            <input type="text" class="form-control" id="searchNotebookForList" placeholder="搜索笔记本"/>
                            <ul class="ztree" id="notebookList"></ul>
                            <ul class="ztree" id="notebookListForSearch"></ul>
                        </div>
                    </div>
                    <div class="folderNote closed" id="myTag">
                        <div class="folderHeader">
                            <i class="fa fa-bookmark fa-left"></i>
                            <span>
									标签
								</span>
                        </div>

                        <ul class="folderBody clearfix" id="tagNav">
                            <li data-tag="red"><a> <span class="label label-red">红色</span></a></li>
                            <li data-tag="blue"><a> <span class="label label-blue">蓝色</span></a></li>
                            <li data-tag="yellow"><a> <span class="label label-yellow">黄色</span></a></li>
                            <li data-tag="green"><a> <span class="label label-green">绿色</span></a></li>
                        </ul>
                    </div>
                    <div class="folderNote closed" id="myShareNotebooks">
                        <div class="folderHeader">
                            <i class="fa fa-user fa-left"></i>
                            <span>
									分享
								</span>
                        </div>

                        <ul class="folderBody" id="shareNotebooks">

                        </ul>
                    </div>
                </div>

                <div id="notebookMin">

                    <div target="#notebookList" title="笔记本" class="minContainer">
                        <i class="fa fa-book"></i>
                        <ul class="dropdown-menu" id="minNotebookList">
                        </ul>
                    </div>
                    <div target="#tagNav" title="我的标签" class="minContainer">
                        <i class="fa fa-bookmark"></i>
                        <ul class="dropdown-menu" id="minTagNav">
                        </ul>
                    </div>
                    <div id="minShareNotebooks">
                        <div class="minContainer" target="#friendContainer0" title="分享">
                            <i class="fa fa-user"></i>
                        </div>
                    </div>
                </div>

            </div>
            <div class="noteSplit" id="notebookSplitter"></div>

            <div id="noteAndEditor">
                <div id="noteAndEditorMask">
                    <img src="/images/loading-24.gif"/>
                    <br />
                    loading...
                </div>
                <div id="noteList">
                    <div class="clearfix" id="notesAndSort" style="position: relative">
                        <div class="pull-left">

                            <div class="dropdown" id="myNotebookNavForListNav">
                                <a class="ios7-a">
                                    <span id="curNotebookForListNote">最新</span>
                                </a>
                            </div>


                            <div class="dropdown" id="sharedNotebookNavForListNav" style="display: none">
                                <a class="ios7-a">
                                    <span id="curSharedNotebookForListNote">最新</span>
                                </a>
                            </div>
                        </div>

                        <div class="pull-left" id="tagSearch"></div>

                        <div id="sortType">
                            <div class="dropdown">
                                <a class="ios7-a dropdown-toggle" id="dropdownMenu1"
                                   data-toggle="dropdown">
                                    </i><i class="fa fa-th-list"></i>
                                </a>
                                <ul class="dropdown-menu" role="menu"
                                    aria-labelledby="dropdownMenu1"
                                >
                                    <li role="presentation"><a data-view="snippet" class="view-style view-snippet" role="menuitem">
                                        <i class="fa fa-check"></i>
                                        摘要视图
                                    </a></li>
                                    <li role="presentation"><a data-view="list" class="view-style view-list" role="menuitem">
                                        <i class="fa fa-check"></i>
                                        列表视图
                                    </a></li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation"><a data-sorter="dateCreatedASC" class="sorter-style sorter-dateCreatedASC" role="menuitem">
                                        <i class="fa fa-check"></i>
                                        创建时间 <i class="fa fa-sort-alpha-asc"></i>
                                    </a></li>
                                    <li role="presentation"><a data-sorter="dateCreatedDESC" class="sorter-style sorter-dateCreatedDESC" role="menuitem">
                                        <i class="fa fa-check"></i>
                                        创建时间 <i class="fa fa-sort-alpha-desc"></i>
                                    </a></li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation"><a data-sorter="dateUpdatedASC" class="sorter-style sorter-dateUpdatedASC" role="menuitem">
                                        <i class="fa fa-check"></i>
                                        更新时间 <i class="fa fa-sort-alpha-asc"></i>
                                    </a></li>
                                    <li role="presentation"><a data-sorter="dateUpdatedDESC" class="sorter-style sorter-dateUpdatedDESC checked" role="menuitem">
                                        <i class="fa fa-check"></i>
                                        更新时间 <i class="fa fa-sort-alpha-desc"></i>
                                    </a></li>
                                    <li role="presentation" class="divider"></li>
                                    <li role="presentation"><a data-sorter="titleASC" class="sorter-style sorter-titleASC" role="menuitem">
                                        <i class="fa fa-check"></i>
                                        标题 <i class="fa fa-sort-alpha-asc"></i>

                                    </a></li>
                                    <li role="presentation"><a data-sorter="titleDESC" class="sorter-style sorter-titleDESC" role="menuitem">
                                        <i class="fa fa-check"></i>
                                        标题 <i class="fa fa-sort-alpha-desc"></i>
                                    </a></li>
                                </ul>
                            </div>
                        </div>
                    </div>


                    <div id="noteItemListWrap">
                        <ul id="noteItemList">
                        </ul>
                    </div>
                </div>

                <div class="noteSplit" id="noteSplitter"></div>

                <div id="note" class="read-only-editor">


                    <div id="noteMask" class="note-mask"></div>

                    <div id="noteMaskForLoading" class="note-mask">
                        <img src="/images/loading-24.gif"/>
                        <br />
                        loading...
                    </div>

                    <div id="editorMask">
                        该笔记本下空空如也...何不
                        <br />
                        <br />
                        <div id="editorMaskBtns">
                            <a class="note">新建普通笔记</a>
                            <a class="markdown">新建Markdown笔记</a>
                        </div>
                        <div id="editorMaskBtnsEmpty">
                            Sorry, 这里不能添加笔记的. 你需要先选择一个笔记本.
                        </div>
                    </div>

                    <div id="batchMask" class="note-mask">
                        <div class="batch-ctn" id="batchCtn"></div>
                        <div class="batch-info">
                            当前选中了 <span></span> 篇笔记
                            <p><i class="fa fa-cog"></i></p>
                        </div>
                    </div>

                    <div id="noteTop">

                        <div id="tool" class="clearfix">




                            <div class="pull-left" id="tag">
                                <div id="tags" style="display: inline-block; line-height: 25px;">
                                </div>
                                <div class="dropdown" style="display: inline-block"
                                     id="tagDropdown">
                                    <i class="fa fa-bookmark-o"></i>
                                    <a
                                            class="metro-a dropdown-toggle" data-toggle="dropdown"
                                            id="addTagTrigger" style="cursor: text; padding-left: 0">
										<span class="add-tag-text">
										点击添加标签
										</span>
                                    </a>
                                    <input type="text" id="addTagInput" />
                                    <ul class="dropdown-menu" role="menu" id="tagColor">
                                        <li role="presentation"><span class="label label-red">红色</span>
                                        </li>
                                        <li role="presentation"><span class="label label-blue">蓝色</span>
                                        </li>
                                        <li role="presentation"><span class="label label-yellow">黄色</span></li>
                                        <li role="presentation"><span class="label label-green">绿色</span></li>
                                    </ul>
                                </div>
                            </div>

                            <ul class="pull-right" id="editorTool">
                                <li><a class="ios7-a " id="editBtn"
                                       data-toggle="dropdown" title="ctrl/cmd+e 编辑与只读切换">
                                    <span class="fa"></span></a></li>
                                <li><a class="ios7-a " id="saveBtn"
                                       data-toggle="dropdown" title="ctrl/cmd+s 保存">
                                    <span class="fa fa-save"></span></a></li>
                                <li class="dropdown" id="noteInfoDropdown">
                                    <a class="ios7-a dropdown-toggle" data-toggle="dropdown" title="信息">
                                        <span class="fa fa-info"></span>
                                    </a>
                                    <div class="dropdown-menu" id="noteInfo"></div>
                                </li>
                                <li class="dropdown" id="attachDropdown">
                                    <a class="ios7-a dropdown-toggle" data-toggle="dropdown" id="showAttach" title="附件">
                                        <span class="fa fa-paperclip"></span>
                                        <span id="attachNum"></span>
                                    </a>
                                    <div class="dropdown-menu" id="attachMenu">
                                        <ul id="attachList">
                                        </ul>
                                        <form id="uploadAttach" method="post" action="/attach/UploadAttach" enctype="multipart/form-data">
                                            <div id="dropAttach" class="dropzone">
                                                <a class="btn btn-success btn-choose-file">
                                                    <i class="fa fa-upload"></i>
                                                    <span>选择文件</span>
                                                </a>
                                                <a class="btn btn-default" id="downloadAllBtn">
                                                    <i class="fa fa-download"></i>
                                                    <span>下载全部</span>
                                                </a>

                                                <input type="file" name="file" multiple/>
                                            </div>
                                            <div id="attachUploadMsg">
                                            </div>
                                        </form>
                                    </div>
                                </li>

                                <li><a class="ios7-a" id="contentHistory"
                                       data-toggle="dropdown" title="历史记录">
                                    <span class="fa fa-history"></span></a></li>
                                <li class="tips-ctn"><a class="ios7-a" target="_blank" href="#"
                                                        title="帮助">
                                    <span class="fa fa-question"></span></a></li>
                            </ul>
                        </div>
                        <div id="noteTitleDiv">
                            <input name="noteTitle" id="noteTitle" value="" placeholder="无标题" tabindex="1" />
                        </div>


                        <div id="noteReadTop">
                            <h2 id="noteReadTitle"></h2>
                            <div class="clearfix" id="noteReadInfo">
                                <i class="fa fa-bookmark-o"></i>
                                <span id="noteReadTags"></span>


                                <i class="fa fa-clock-o"></i> 更新
                                <span id="noteReadUpdatedTime"></span>


                                <i class="fa fa-clock-o"></i> 创建
                                <span id="noteReadCreatedTime"></span>
                            </div>
                        </div>
                    </div>
                    <div id="editor" class="read-only">

                        <div id="mceToolbar">
                            <div id="mceToolbarContainer">
                                <div id="popularToolbar" style="position: absolute; right: 30px; left: 0"></div>
                                <a id="moreBtn"> <i class="fa more-fa"></i></a>
                            </div>

                            <div id="infoToolbar" class="info-toolbar invisible">

                                <span class="lang">创建</span>: <span class="created-time"></span>
                                <span class="lang">更新</span>: <span class="updated-time"></span>
                            </div>
                        </div>
                        <div class="editorBg"></div>
                        <div id="leanoteNav" class="leanoteNav">
                            <h1>
                                <i class="fa fa-align-justify" title="文档导航"></i>
                                <span>文档导航</span>
                            </h1>
                            <div id="leanoteNavContent" class="leanoteNavContent">
                            </div>
                        </div>

                        <form id="upload" method="post" action="/file/uploadImageLeaui" enctype="multipart/form-data" style="margin-top: 5px;">
                            <div id="drop">
                                将图片拖动至此
                                <input type="file" name="file" multiple style="display: none"/>
                            </div>
                            <ul id="uploadMsg">
                            </ul>
                        </form>


                        <div id="editorContent" name="editorContent" tabindex="2" /></div>
                </div>

                <div id="mdEditor" class="read-only">
                    <div class="layout-wrapper-l1">
                        <div class="layout-wrapper-l2">
                            <div class="navbar navbar-default">
                                <div class="navbar-inner" id="wmd-button-bar">
                                    <div id="mdInfoToolbar" class="info-toolbar invisible">
                                        <span class="lang">创建</span>: <span class="created-time"></span>
                                        <span class="lang">更新</span>: <span class="updated-time"></span>
                                    </div>

                                    <div class="wmd-button-bar-inner clearfix">
                                        <ul class="nav left-buttons">
                                            <li class="wmd-button-group1 btn-group"></li>
                                        </ul>
                                        <ul class="nav left-buttons">
                                            <li class="wmd-button-group2 btn-group"></li>
                                        </ul>
                                        <ul class="nav left-buttons">
                                            <li class="wmd-button-group3 btn-group"></li>
                                        </ul>
                                        <ul class="nav left-buttons">
                                            <li class="wmd-button-group4 btn-group"></li>
                                        </ul>

                                        <ul class="nav left-buttons">
                                            <li class="wmd-button-group6 btn-group">
                                            <li class="wmd-button btn btn-success" id="wmd-help-button" title="Markdown 语法" style="left: 0px; display: none;"><span style="display: none; background-position: 0px 0px;"></span><i class="fa fa-question-circle"></i></li>
                                            </li>
                                        </ul>


                                    </div>
                                </div>
                                <div class="editorBg"></div>
                            </div>
                            <div class="layout-wrapper-l3">
                                <div id="left-column">
                                    <pre id="wmd-input" class="form-control"><div class="editor-content mousetrap" contenteditable=true></div><div class="editor-margin"></div></pre>

                                    <div class="textarea-helper"></div>
                                </div>
                                <div id="right-column">
                                    <div class="preview-panel panel-open" id="preview-panel">
                                        <div id="mdSplitter2" class="layout-resizer layout-resizer-preview open" style="-webkit-user-select: none; -webkit-user-drag: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); touch-action: none;"></div>
                                        <div class="layout-toggler layout-toggler-preview btn btn-info open" title="Toggle preview" data-open="1"><i class="fa fa-angle-right"></i></div>
                                        <div class="preview-container">
                                            <div id="preview-contents">
                                                <div id="wmd-preview" class="preview-content"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="extension-preview-buttons">
                                <div id="leanoteNavMd" class="leanoteNav">
                                    <h1>
                                        <i class="fa fa-align-justify" title="文档导航"></i>
                                        <span>文档导航</span>
                                    </h1>
                                    <div id="leanoteNavContentMd" class="leanoteNavContent table-of-contents">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="wmd-button-bar" class="hide"></div>
                    </div>


                </div>
            </div>
        </div>



        <div class="modal fade modal-insert-image">
            <div class="modal-dialog"  style="width: 840px;max-width:100%;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">&times;</button>
                        <h4 class="modal-title">图片</h4>
                    </div>
                    <div class="modal-body" style="padding-top: 0; padding-bottom: 0">
                        <iframe name="mdImageManager" style="width: 100%; height: 350px" scrolling="no" id="leauiIfrForMD" src="" frameborder="0"></iframe>
                    </div>
                    <div class="modal-footer">
                        <a href="#" class="btn btn-default"
                           data-dismiss="modal">取消</a> <a href="#"
                                                          class="btn btn-primary action-insert-image" data-dismiss="modal">插入</a>
                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade bs-modal-sm" id="editorDialog" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="editorDialog-title"></h4>
                    </div>
                    <div class="modal-body">
                        <p></p>
                        <div class="input-group">
							  <span class="input-group-addon">
							  	<i></i>
							  </span>
                            <input type="text" class="form-control" placeholder="">
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" id="editorDialog-confirm">确认</button>
                    </div>

                </div>
            </div>
        </div>

        <div class="modal fade bs-modal-sm" id="leanoteDialogRemote" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
        </div>

        <div class="modal fade bs-modal-sm" id="leanoteDialog" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="modalTitle">操作</h4>
                    </div>

                    <div class="modal-body">
                        内容区
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>

                </div>
            </div>
        </div>

        <div class="modal fade bs-modal-sm" id="sendRegisterEmailDialog" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" class="modalTitle">邀请好友</h4>
                    </div>

                    <div class="modal-body">
                        <form role="form">
                            <div class="form-group">
                                <div id="registerEmailMsg" class="alert alert-warning" style="display: none">
                                </div>
                                <input type="hidden" id="toEmail"/>
                                <label for="emailContent">邮件内容</label>
                                <textarea class="form-control" id="emailContent">Hi, 我是life, leanote非常好用, 快来注册吧.</textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-success" id="registerEmailBtn">发送</button>
                    </div>

                </div>
            </div>
        </div>


        <div class="modal fade bs-modal-sm" id="setThemeDialog" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" class="modalTitle">主题</h4>
                    </div>
                    <div class="modal-body">
                        <table id="themeForm">
                            <tr>
                                <td>
                                    <label>
                                        <img src="/images/slider/all.gif" height="100px">
                                        <br />
                                        <input type="radio" name="theme" value="default"> 默认
                                    </label>
                                </td>
                                <td>
                                    <label>
                                        <img src="/images/slider/all-simple.gif" height="100px">
                                        <br />
                                        <input type="radio" name="theme" value="simple"> 简约
                                    </label>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="display: hidden">
            <div class="hide" id="copyDiv"></div>
        </div>
    </div>
</div>
<script>
    var LEA = {};
    LEA.sPath = "https://localhost:8080";
    LEA.locale = "zh-cn";
    var UrlPrefix = 'https://localhost:8080';
    var UrlPrefixHttps = 'https://localhost:8080';
    var UserInfo = {};
    var notebooks = [];
    var shareNotebooks = {};
    var sharedUserInfos = [];
    var curNoteId = '';
    var curNotebookId = '';
    var curSharedNoteNotebookId = '';
    var curSharedUserId = '';
    var notes = [];
    var latestNotes = [];
    var noteContentJson = {};
    var tagsJson = [];
    var GlobalConfigs = {};
    var trialOrPremiumIsOver =  false ;
    var userIsNormal =  true ;
    var tinyMCEPreInit = {base: '/tinymce', suffix: '.min'};
</script>
<script src="/js/i18n/msg.zh-cn.js?t=f"></script>
<script src="/js/dep.min.js?t=3"></script>
<script src="/tinymce/tinymce.full.min.js?t=20"></script>

<script src="/libs/ace/ace.js?t=20"></script>
<script src="/js/app.min.js?t=r14"></script>
<script>
    initPage("${user.email}");
    window.require = {
        baseUrl: 'https://localhost:8080',
    };
</script>
<script src="/js/markdown-v2.min.js?t=o2"></script>
<script src="/js/plugins/main.min.js?t=a"></script>
<script>
    var _hmt = _hmt || [];
    (function() {
        setTimeout(function () {
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?0c0239760addb3bfc082d763a601610a";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        }, 5000);
    })();
</script>
</body>
</html>
