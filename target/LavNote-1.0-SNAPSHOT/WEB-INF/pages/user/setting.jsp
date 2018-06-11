<%--
  Created by IntelliJ IDEA.
  User: youyujie
  Date: 2018/6/11
  Time: 上午10:52
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


    <link rel="manifest" href="manifests/homescreen-manifest.json">
    <title>Paperwork</title>

    <!-- <link media="all" type="text/css" rel="stylesheet" href="/css/bootstrap.min.css">
 -->
    <!-- <link media="all" type="text/css" rel="stylesheet" href="/css/bootstrap-theme.min.css">
 -->

    <link media="all" type="text/css" rel="stylesheet" href="/css/themes/paperwork-v1.min.css">


    <link media="all" type="text/css" rel="stylesheet" href="/css/freqselector.min.css">


    <link media="all" type="text/css" rel="stylesheet" href="/css/typeahead.min.css">


    <link media="all" type="text/css" rel="stylesheet" href="/css/mathquill.css">


    <link media="all" type="text/css" rel="stylesheet" href="/css/libs.css">


    <link media="all" type="text/css" rel="stylesheet" href="/css/bootstrap-editable.css">


</head>
<body ng-app="paperworkNotes">
<div ng-controller="ConstructorController"></div>

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="paperwork-logo navbar-brand transition-effect" href="/#/"
            ><img src="/images/navbar-logo.png"> Paperwork</a>
        </div>
        <div class="navbar-collapse collapse">
            <div class="visible-xs">
                <form class="navbar-form" role="form">
                    <div class="form-group" ng-controller="SidebarNotebooksController">
                        <select class="form-control navbar-search" ng-model="notebookSelectedId"
                                ng-options="notebook.id as (notebook.title) for notebook in notebooks"
                                ng-change="openNotebook(notebookSelectedId,0,notebookSelectedId)">
                        </select>
                    </div>
                    <div class="form-group" ng-controller="NotesListController">
                        <select class="form-control navbar-search" ng-model="noteSelectedId.noteId"
                                ng-options="n.id as ((n.version.title || n.title)) for n in notes"
                                ng-change="openSelectedNote()">
                        </select>
                    </div>
                </form>
            </div>

            <ul class="nav navbar-nav animate-panel" ng-cloak ng-show="navbarMainMenu">
                <li class="dropdown">
                    <a id="menu-item-file" href="" class="dropdown-toggle transition-effect" data-toggle="dropdown">File <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li ng-controller="SidebarNotesController" class="{{ menuItemNotebookClass() }}">
                            <a id="menu-item-file-sub-new_note" href="" ng-click="newNote(getNotebookSelectedId())"><i class="fa fa-file"></i> New note</a>
                        </li>
                        <li ng-controller="SidebarNotebooksController">
                            <a id="menu-item-file-sub-new_notebook" href="" ng-click="modalNewNotebook()"><i class="fa fa-book"></i> New notebook</a>
                        </li>
                        <li ng-controller="SidebarNotebooksController">
                            <a id="menu-item-file-sub-new_collection" href="" ng-click="modalNewCollection()"><i class="fa fa-folder"></i> New collection</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a id="menu-item-edit" href="" class="dropdown-toggle transition-effect" data-toggle="dropdown">Edit <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li ng-controller="SidebarNotesController" class="{{ menuItemNoteClass('single') }}">
                            <a id="menu-item-edit-sub-edit_note" href="" ng-click="editNote(getNotebookSelectedId(), (getNoteSelectedId(true).noteId))"><i class="fa fa-pencil"></i> Edit note</a>
                        </li>
                        <li ng-controller="SidebarNotesController" class="{{ menuItemNoteClass('multiple') }}">
                            <a id="menu-item-edit-sub-edit_notes" href="" ng-click="editNotes(getNotebookSelectedId())"><i class="fa fa-files-o"></i> <span ng-hide="editMultipleNotes">Edit multiple notes...</span><span ng-show="editMultipleNotes">Stop editing multiple notes</span></a>
                        </li>
                        <li ng-controller="SidebarNotesController" class="{{ menuItemNoteClass('multiple') }}">
                            <a id="menu-item-edit-sub-move_note" href="" ng-click="modalMoveNote(getNotebookSelectedId(), (getNoteSelectedId(true)).noteId)"><i class="fa fa-arrow-right"></i> <span ng-hide="editMultipleNotes">Move note</span><span ng-show="editMultipleNotes">Move notes</span></a>
                        </li>
                        <li ng-controller="SidebarNotesController" class="{{ menuItemNoteClass('multiple') }}">
                            <a id="menu-item-edit-sub-share_note" href="" ng-click="modalShareNote(getNotebookSelectedId(), (getNoteSelectedId(true)).noteId)"><i class="fa fa-share-alt"></i> <span ng-hide="editMultipleNotes">Share note</span><span ng-show="editMultipleNotes">Share notes</span></a>
                        </li>
                        <li ng-controller="SidebarNotesController" class="{{ menuItemNoteClass('multiple') }}">
                            <a id="menu-item-edit-sub-delete_note" href="" ng-click="modalDeleteNote(getNotebookSelectedId(), (getNoteSelectedId(true)).noteId)"><i class="fa fa-trash-o"></i> <span ng-hide="editMultipleNotes">Delete note</span><span ng-show="editMultipleNotes">Delete notes</span></a>
                        </li>
                        <li class="divider"></li>
                        <li ng-controller="SidebarNotebooksController" class="{{ menuItemNotebookClass() }}">
                            <a id="menu-item-edit-sub-edit_notebook" href="" ng-click="modalEditNotebook(getNotebookSelectedId())"><i class="fa fa-pencil"></i> Edit notebook</a>
                        </li>
                        <li ng-controller="SidebarNotebooksController" class="{{ menuItemNotebookClass() }}">
                            <a id="menu-item-edit-sub-share_notebook" href="" ng-click="modalShareNotebook(getNotebookSelectedId())"><i class="fa fa-share-alt"></i> Share notebook</a>
                        </li>
                        <li ng-controller="SidebarNotebooksController" class="{{ menuItemNotebookClass() }}">
                            <a id="menu-item-edit-sub-delete_notebook" href="" ng-click="modalDeleteNotebook(getNotebookSelectedId())"><i class="fa fa-trash-o"></i> Delete notebook</a>
                        </li>
                    </ul>
                </li>
            </ul>

            <form ng-controller="SidebarNotesController" ng-cloak ng-show="navbarSearchForm" class="navbar-form navbar-left animate-panel" id="searchForm" role="form" ng-submit="submitSearch()">
                <div class="form-group">
                    <input type="text" class="form-control navbar-search" placeholder="Search..." ng-model="search">
                </div>
            </form>

            <ul class="nav navbar-nav navbar-right">
                <!-- Show hover titles for small and medium screens -->
                <li class="hidden-xs hidden-lg" title="Library">
                    <a href="#/" class="transition-effect">
                        <i class="fa fa-book"></i>
                    </a>
                </li>

                <!-- Show actual titles for large and extra small screens -->
                <li class="hidden-sm hidden-md">
                    <a href="#/" class="transition-effect">
                        <i class="fa fa-book"></i>
                        <span>
			Library
		</span>
                    </a>
                </li>

                <!-- Show hover titles for small and medium screens -->
                <li class="hidden-xs hidden-lg" title="Profile">
                    <a href="/profile" class="transition-effect">
                        <i class="fa fa-user"></i>
                    </a>
                </li>

                <!-- Show actual titles for large and extra small screens -->
                <li class="hidden-sm hidden-md">
                    <a href="/profile" class="transition-effect">
                        <i class="fa fa-user"></i>
                        <span>
			Profile
		</span>
                    </a>
                </li>

                <!-- Show hover titles for small and medium screens -->
                <li class="hidden-xs hidden-lg" title="Settings">
                    <a href="/settings" class="transition-effect">
                        <i class="fa fa-cog"></i>
                    </a>
                </li>

                <!-- Show actual titles for large and extra small screens -->
                <li class="hidden-sm hidden-md">
                    <a href="/settings" class="transition-effect">
                        <i class="fa fa-cog"></i>
                        <span>
			Settings
		</span>
                    </a>
                </li>


                <!-- Show hover titles for small and medium screens -->
                <li class="hidden-xs hidden-lg" title="Help">
                    <a href="/help" class="transition-effect">
                        <i class="fa fa-question"></i>
                    </a>
                </li>

                <!-- Show actual titles for large and extra small screens -->
                <li class="hidden-sm hidden-md">
                    <a href="/help" class="transition-effect">
                        <i class="fa fa-question"></i>
                        <span>
			Help
		</span>
                    </a>
                </li>
                <!-- Show hover titles for small and medium screens -->
                <li class="hidden-xs hidden-lg" title="Sign out">
                    <a href="/logout" class="transition-effect">
                        <i class="fa fa-sign-out"></i>
                    </a>
                </li>

                <!-- Show actual titles for large and extra small screens -->
                <li class="hidden-sm hidden-md">
                    <a href="/logout" class="transition-effect">
                        <i class="fa fa-sign-out"></i>
                        <span>
			Sign out
		</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>


<div class="container-narrow" ng-controller="SettingsController">
    <h1>Settings</h1>

    <ul class="nav nav-tabs nav-tabs-margin" role="tablist">
        <li class="active"><a href="#about" role="tab" data-toggle="tab">About</a></li>
        <li><a href="#language" role="tab" data-toggle="tab">Language</a></li>
        <li><a href="#client" role="tab" data-toggle="tab" ng-click="getTabContent('client')">Client</a></li>
        <li><a href="#import" role="tab" data-toggle="tab">Import/Export</a></li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane fade in active" id="about">
            <div class="form-group">
                <label class="control-label">
                    About Paperwork
                </label>
                <p class="help-block">Please include this identification code in any issues you open on our Github repository. This code does not contain any personal information and can be shared freely. </p>
                <div class="text-center">

                </div>
            </div>	</div>
        <div class="tab-pane fade" id="language">
            <form method="POST" action="/settings" accept-charset="UTF-8" id="form-language" class="form-horizontal" role="form"><input name="_token" type="hidden" value="PJMORKp4Qe2fdzNBkrVuXZehqQa4nFFzQjsWCufo">
                <div class="form-group ">
                    <label for="ui_language" class="col-sm-5 control-label">User Interface Language</label>
                    <div class="col-sm-7">
                        <select id="ui_language" class="form-control" name="ui_language"><option value="tr">Turkish</option><option value="ach">languages.ach</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="hu">Hungarian</option><option value="ru">Russian</option><option value="de">German</option><option value="zh-Hant">languages.zh-Hant</option><option value="zh">languages.zh</option><option value="pl">Polish</option><option value="nl">Dutch</option><option value="zh-Hans">languages.zh-Hans</option><option value="sp">Spanish</option><option value="cs">languages.cs</option><option value="es_ES">languages.es_ES</option><option value="zh_HK">languages.zh_HK</option><option value="fr">French</option><option value="en" selected="selected">English</option><option value="uk">Ukranian</option><option value="chi-sim">Chinese (Simplified)</option><option value="pt_BR">Portuguese (Brazil)</option><option value="hu_HU">languages.hu_HU</option><option value="sv">Swedish</option></select>
                    </div>
                </div>

                <div class="form-group ">
                    <label for="document_languages" class="col-sm-5 control-label">
                        Document Languages
                        <p class="label-description">The languages you select here will be used for parsing text within attachments you upload, allowing you to search for the content of these. An attachment could be a photo of a document you took with your smartphone, for example. Select the languages these documents are usually written in.</p>
                    </label>
                    <div class="col-sm-7">
                        <div class="container-scrollable">
                            <div class="container">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="afr"> Afrikaans
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="sqi"> Albanian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="grc"> Ancient Greek
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="ara"> Arabic
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="aze"> Azerbaijani
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="eus"> Basque
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="bel"> Belarusian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="ben"> Bengali
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="bul"> Bulgarian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="cat"> Catalan
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="chr"> Cherokee
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input checked="checked" name="document_languages[]" type="checkbox" value="chi-sim"> Chinese (Simplified)
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="chi-tra"> Chinese (Traditional)
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="hrv"> Croatian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="ces"> Czech
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="dan"> Danish
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="nld"> Dutch
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="eng"> English
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="epo"> Esperanto
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="est"> Estonian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="fin"> Finnish
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="frk"> Frankish
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="fra"> French
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="glg"> Galician
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="deu"> German
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="ell"> Greek
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="heb"> Hebrew
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="hin"> Hindi
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="hun"> Hungarian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="isl"> Icelandic
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="ind"> Indonesian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="ita"> Italian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="jpn"> Japanese
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="kan"> Kannada
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="kor"> Korean
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="lav"> Latvian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="lit"> Lithuanian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="mkd"> Macedonian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="msa"> Malay
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="mal"> Malayalam
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="mlt"> Maltese
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="equ"> Math (formulas & calculations)
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="enm"> Middle English
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="frm"> Middle French
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="nor"> Norwegian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="pol"> Polish
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="por"> Portuguese
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="ron"> Romanian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="rus"> Russian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="srp"> Serbian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="slk"> Slovakian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="slv"> Slovenian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="spa"> Spanish
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="swa"> Swahili
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="swe"> Swedish
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="tgl"> Tagalog
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="tam"> Tamil
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="tel"> Telugu
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="tha"> Thai
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="tur"> Turkish
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="ukr"> Ukranian
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="checkbox">
                                            <label>
                                                <input name="document_languages[]" type="checkbox" value="vie"> Vietnamese
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-5 col-sm-7">
                        <input id="submit-language" class="btn btn-primary" type="submit" value="Save">
                    </div>
                </div>
            </form>
        </div>
        <div class="tab-pane fade" id="client">
            <div ng-bind-html="tabs.client.content"></div>
            <div ng-hide="tabs.client.isLoaded" class="load3 text-center">
                <div class="loader"></div>
                <h3>Loading...</h3>
            </div>
        </div>
        <div class="tab-pane fade" id="import">
            <form method="POST" action="/settings/import" accept-charset="UTF-8" id="form-import" class="form" role="form" enctype="multipart/form-data"><input name="_token" type="hidden" value="PJMORKp4Qe2fdzNBkrVuXZehqQa4nFFzQjsWCufo">
                <div class="form-group">
                    <label for="exampleInputFile">Import an Evernote XML:: (Experimental)</label>
                    <input name="enex" type="file">
                    <p class="help-block">Upload your Evernote XML export here, to import your Evernote notes into Paperwork.</p>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-5 col-sm-7">
                        <input id="submit-import" class="btn btn-primary" type="submit" value="Import">
                    </div>
                </div>
            </form>
            <br/><br/><br/>
            <form method="POST" action="/settings/export" accept-charset="UTF-8" id="form-export" class="form" role="form"><input name="_token" type="hidden" value="PJMORKp4Qe2fdzNBkrVuXZehqQa4nFFzQjsWCufo">
                <div class="form-group">
                    <label class="control-label">
                        Export as Evernote XML: (Experimental)
                    </label>
                    <p class="help-block">Download an ENEX file compatible with Evernote to move your notes from Paperwork. </p>
                    <div class="col-sm-offset-5 col-sm-7">
                        <input id="submit-export" class="btn btn-primary" type="submit" value="Export">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="footer footer-issue hide" ondblclick="$(this).hide()">
    </div>
</div>

<script src="/js/jquery.min.js"></script>


<script src="/ckeditor/plugins/codesnippet/lib/highlight/highlight.pack.js"></script>

<script src="/js/angular.min.js"></script>


<script src="/js/paperwork.min.js"></script>

<script src="/js/paperwork-native.min.js"></script>


<script src="/js/bootstrap.min.js"></script>

<script src="/js/tagsinput.min.js"></script>

<script src="/js/libraries.min.js"></script>


<script src="/ckeditor/ckeditor.js"></script>



<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="/js/ltie9compat.min.js"></script>

<![endif]-->
<!--[if lt IE 11]>
<script src="/js/ltie11compat.js"></script>

<![endif]-->

</body>
</html>
