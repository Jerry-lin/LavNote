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
                <li class="hidden-xs hidden-lg" title="Admin Area">
                    <a href="/admin" class="transition-effect">
                        <i class="fa fa-star"></i>
                    </a>
                </li>

                <!-- Show actual titles for large and extra small screens -->
                <li class="hidden-sm hidden-md">
                    <a href="/admin" class="transition-effect">
                        <i class="fa fa-star"></i>
                        <span>
			Admin Area
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


<div ng-controller="MessageBoxController" class="modal fade" id="modalMessageBox" tabindex="-1" role="dialog" aria-labelledby="modalMessageBoxLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="" accept-charset="UTF-8" class="form-signin" role="form"><input name="_token" type="hidden" value="z3TqkUmmBWPxsuYZSlgHk75BtGJzOabv8wZMKSOX">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="modalMessageBoxLabel">
                        {{ modalMessageBox.title }}
                    </h4>
                </div>
                <div class="modal-body">
                    {{ modalMessageBox.content }}
                </div>
                <div class="modal-footer">
                    <button ng-repeat="button in modalMessageBox.buttons" type="button" class="btn {{ button.class ? button.class : 'btn-default' }}" data-dismiss="{{ button.isDismiss ? 'modal' : '' }}" ng-click="onClick(button.id)">{{ button.label }}</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div ng-controller="SidebarNotebooksController" class="modal fade" id="modalNotebook" tabindex="-1" role="dialog" aria-labelledby="modalNotebookLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" accept-charset="UTF-8" class="form ng-pristine ng-invalid ng-invalid-required" role="form" ng-submit="modalNotebookSubmit()">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="modalNotebookLabel">
                        {{ modalNotebook.action == 'create' ? 'New notebook' : '' }}
                        {{ modalNotebook.action == 'edit' ? 'Edit notebook' : '' }}
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group ">
                        <input ng-model="modalNotebook.title" class="form-control" placeholder="Enter notebook title" required="required" autofocus="autofocus" name="title" type="text" value="">
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="add_shortcut" ng-model="modalNotebook.shortcut"> Add this notebook to my shortcuts
                        </label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" ng-click="modalNotebookSubmit()">
                        {{ modalNotebook.action == 'create' ? 'Create' : '' }}
                        {{ modalNotebook.action == 'edit' ? 'Update' : '' }}
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<div ng-controller="SidebarNotebooksController" class="modal fade" id="modalNotebookSelect" tabindex="-1" role="dialog" aria-labelledby="modalNotebookSelectLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="" accept-charset="UTF-8" class="form-signin" role="form"><input name="_token" type="hidden" value="z3TqkUmmBWPxsuYZSlgHk75BtGJzOabv8wZMKSOX">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="modalNotebookSelectLabel">
                        {{ modalMessageBox.header }}
                    </h4>
                </div>
                <div class="modal-body">
                    <div ng-if="(modalMessageBox.description)">
                        <p>{{ modalMessageBox.description }}</p>
                    </div>
                    <div class="container-scrollable">
                        <div class="container">
                            <form id="notebook-select" name="notebook-select">
                                <div ng-repeat="notebook in writableNotebooks | orderBy:'title'">
                                    <div ng-if="(notebook.id != modalMessageBox.notebookId)">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="notebookSelectedModel" ng-model="$parent.$parent.notebookSelectedModel" value="{{ notebook.id }}"> {{ notebook.title }}
                                            </label>
                                        </div>
                                    </div>
                                    <!--<div ng-repeat="child in notebooks.children | orderBy:'title'">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="notebookSelectedModel" ng-model="$parent.notebookSelectedModel" value="{{ child.id }}"> {{ child.title }}
                                            </label>
                                        </div>
                                    </div>-->
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" ng-click="modalNotebookSelectSubmit(modalMessageBox.notebookId, modalMessageBox.noteId, notebookSelectedModel)">Select</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div ng-controller="SidebarManageTagsController" class="modal fade modal-manage-list" id="modalManageTags" tabindex="-1" role="dialog" aria-labelledby="modalManageTagsLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modalManageTagsLabel">
                    Manage tags
                </h4>
            </div>
            <div class="modal-body">
                <div class="manage-list-content">
                    <div class="row" ng-repeat="tag in modalTags | orderBy:'title':reverse" pw-on-finish-render="ngRepeatFinished">
                        <div class="col-sm-10">
                            <a class="line" href="#" data-name="title" data-type="text" data-pk="{{tag.id}}">{{tag.title}}</a>
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-xs btn-danger" ng-click="deleteTag(tag.id)"><i class="fa fa-trash-o"></i></button>
                        </div>
                        <div ng-repeat="child in tag.children | orderBy:'title':reverse" pw-on-finish-render="ngRepeatFinished">
                            <div class="col-sm-8">
                                <a class="line" href="#" data-name="title" data-type="text" data-pk="{{child.id}}">{{child.title}}</a>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-xs btn-info" ng-click="unNestTag(child.id)"><i class="fa fa-unlink"></i></button>
                            </div>
                            <div class="col-sm-2 pull-right">
                                <button class="btn btn-xs btn-danger" ng-click="deleteTag(child.id)"><i class="fa fa-trash-o"></i></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div ng-controller="SidebarManageNotebooksController" class="modal fade modal-manage-list" id="modalManageNotebooks" tabindex="-1" role="dialog" aria-labelledby="modalManageNotebooksLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modalManageNotebooksLabel">
                    Manage notebooks
                </h4>
            </div>
            <div class="modal-body">
                <div class="manage-list-content">
                    <div class="row" ng-repeat="item in modalList | orderBy:'title':reverse" pw-on-finish-render="ngRepeatFinished">
                        <div class="col-sm-4">
                            <a class="{{item.css_class}}" href="#" data-name="title" data-type="text" data-pk="{{item.id}}" ng-click="editLineCalled(item.id, item.type)">{{item.title}}</a>
                        </div>
                        <div class="col-sm-6">
                            <button class="btn btn-xs btn-default" ng-click="removeFromCollection(item.id)" ng-if="(item.type == 0 && item.parent_id != NULL)">Remove from collection</button>
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-xs btn-danger" ng-click="deleteItem(item.id, item.type)"><i class="fa fa-trash-o"></i></button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary pull-left" ng-click="addNotebook();">New notebook</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<div ng-controller="SidebarNotesController" class="modal fade" id="modalUsersSelect" tabindex="-1" role="dialog" aria-labelledby="modalUsersSelectLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="" accept-charset="UTF-8" class="form-signin" role="form"><input name="_token" type="hidden" value="z3TqkUmmBWPxsuYZSlgHk75BtGJzOabv8wZMKSOX">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="modalUsersSelectLabel">
                        Invite users to share the note(s)
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="container-scrollable">
                        <form id="user-select" name="user-select">
                            <div ng-repeat="user in users | orderBy:'firstname'">
                                <div ng-hide="(user.is_current_user)">
                                    <div class="user-row">
                                        <i class="fa fa-warning" title="This user is owner of a selected note or notebook." ng-show="(user.owner)"></i>
                                        {{ user.firstname }} {{user.lastname}}
                                        <select class="perm-select" ng-model="user.umask" ng-options="u.value as (u.name) for u in umasks">
                                        </select>
                                    </div>
                                </div>
                                <div ng-show="(user.is_current_user)">
                                    <div class="user-row">
                                        {{ user.firstname }} {{user.lastname}}
                                        <div class="perm-select">
                                            This is yourself!
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-left" ng-click="modalUsersSelectInherit(modalMessageBox.notebookId)">Inherit from notebook</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" ng-click="modalUsersSelectSubmit(modalMessageBox.notebookId, modalMessageBox.noteId, users)">Select</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div ng-controller="SidebarNotebooksController" class="modal fade" id="modalUsersNotebookSelect" tabindex="-1" role="dialog" aria-labelledby="modalUsersNotebookSelectLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="" accept-charset="UTF-8" class="form-signin" role="form"><input name="_token" type="hidden" value="z3TqkUmmBWPxsuYZSlgHk75BtGJzOabv8wZMKSOX">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="modalUsersSelectLabel">
                        Invite users to share the notebook
                    </h4>
                </div>
                <div class="modal-body">
                    <div ng-show="showWarningNotebook">You are not the owner of the selected notebook. You will not be able to share it with other users. You can still share the notes you own that belong to this notebook.</div>
                    <div ng-show="showWarningNotes">You do not own any notes in the selected notebook. You will not be able to share anything.</div>
                    <div class="container-scrollable">
                        <form id="user-select" name="user-select">
                            <div ng-repeat="user in users | orderBy:'firstname'">
                                <div ng-hide="(user.is_current_user)">
                                    <div class="user-row">
                                        <i class="fa fa-warning" title="This user is owner of a selected note or notebook." ng-show="(user.owner)"></i>
                                        {{ user.firstname }} {{user.lastname}}
                                        <select class="perm-select" ng-model="user.umask" ng-options="u.value as (u.name) for u in umasks">
                                        </select>
                                    </div>
                                </div>
                                <div ng-show="(user.is_current_user)">
                                    <div class="user-row">
                                        {{ user.firstname }} {{user.lastname}}
                                        <div class="perm-select">
                                            This is yourself!
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="permission_propagation" ng-model="propagationToNotes" ng-change="modalUsersNotebookSelectCheck(modalMessageBox.notebookId,propagationToNotes)"> Propagate sharing status to the notes currently in the notebook?
                        </label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" ng-click="modalUsersNotebookSelectSubmit(modalMessageBox.notebookId, users, propagationToNotes)">Select</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div ng-controller="SidebarNotebooksController" class="modal fade" id="modalCollection" tabindex="-1" role="dialog" aria-labelledby="modalCollectionLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" accept-charset="UTF-8" class="form ng-pristine ng-invalid ng-invalid-required" role="form" ng-submit="modalCollectionSubmit()">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="modalCollectionLabel">
                        {{ modalCollection.action == 'create' ? 'New Collection' : '' }}
                        {{ modalCollection.action == 'edit' ? 'Edit Collection' : '' }}
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group ">
                        <input ng-model="modalCollection.title" class="form-control" placeholder="Enter collection title" required="required" autofocus="autofocus" name="title" type="text" value="">
                    </div>
                    <div class="container-scrollable" id="modalCollectionNotebookCheckboxes">
                        <div class="container">
                            <div ng-repeat="notebook in writableNotebooks | orderBy:'title'">
                                <div ng-hide="(notebook.id == 0 || notebook.id == modalMessageBox.notebookId)">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="selectedNotebooksForCollection" ng-model="selectedNotebooksForCollection[notebook.id]" value="{{ notebook.id }}"> {{ notebook.title }}
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" ng-click="modalCollectionSubmit()">
                        {{ modalCollection.action == 'create' ? 'Create' : '' }}
                        {{ modalCollection.action == 'edit' ? 'Update' : '' }}
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<div ng-controller="SidebarNotebooksController" class="modal fade" id="modalNotebookDelete" tabindex="-1" role="dialog" aria-labelledby="modalNotebookDeleteLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" accept-charset="UTF-8" class="form ng-pristine ng-invalid ng-invalid-required" role="form" ng-submit="modalNotebookDeleteSubmit()">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="modalNotebookDeleteLabel">Delete notebook{{ modalNotebookDelete.notebookTitle ? ' ' + modalNotebookDelete.notebookTitle : '' }}?</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group ">
                        This will delete the selected notebook and all its notes. Are you sure?
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="delete_notes_inside" ng-model="modalNotebookDelete.delete_notes"> Delete the notes in this notebook
                        </label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-warning" ng-click="modalNotebookDeleteSubmit()">Yes</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <section pw-status-notification></section>
        <div class="col-md-5 col-sm-7 hidden-xs">
            <div class="fa sidebar-collapse-switch" ng-show="!expandedNoteLayout"
                 ng-class="sidebarCollapsed ? 'fa-chevron-right sidebar-collapse-switch-closed' : 'fa-chevron-left col-sm-offset-3 col-md-offset-2'"
                 ng-click="sidebarCollapsed = !sidebarCollapsed" ng-init="sidebarCollapsed = false"></div>
            <div id="sidebarNotebooks" class="col-sm-3 col-md-2 sidebar hidden-xs animate-panel disable-selection" ng-controller="SidebarNotebooksController" ng-show="isVisible()" ng-hide="sidebarCollapsed" ng-init="initialiseSidebar()">
                <ul class="nav nav-sidebar sidebar-no-border" ng-hide="sidebarCollapsed">
                    <div class="tree">
                        <ul class="tree-base">
                            <li>
                                <span class="tree-header tree-header-shortcuts" title="Click to {{ shortcutsCollapsed ? 'Expand' : 'Collapse' }}" ng-click="shortcutsCollapsed=!shortcutsCollapsed"><i class="fa {{ shortcutsCollapsed ? 'fa-chevron-right' : 'fa-chevron-down' }}"></i> Shortcuts</span>
                                <ul class="tree-child" collapse="shortcutsCollapsed">
                                    <li class="tree-notebook" ng-repeat="shortcut in shortcuts | orderBy:'sortkey'" ng-cloak>
                                        <span ng-click="openNotebook(shortcut.id, shortcut.type, notebook.id)" ng-class="{ 'active': notebook.id == getNotebookSelectedId() }"><i class="fa fa-book"></i> {{shortcut.title}}</span>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <span class="tree-header tree-header-notebooks" title="Click to {{ notebooksCollapsed ? 'Expand' : 'Collapse' }}" ng-click="notebooksCollapsed=!notebooksCollapsed"><i class="fa {{ notebooksCollapsed ? 'fa-chevron-right' : 'fa-chevron-down' }}"></i> Notebooks <button class="btn btn-default btn-xs pull-right" ng-click="modalManageNotebooks();$event.stopPropagation();" title="Manage notebooks"><span class="fa fa-pencil"></span></button></span>
                                <ul class="tree-child" collapse="notebooksCollapsed">
                                    <li class="tree-notebook" ng-repeat="notebook in notebooks | orderBy:'title'" ng-cloak>
                                        <div class="notebook-title" ng-click="openNotebook(notebook.id, notebook.type, notebook.id)" ng-class="{ 'active': notebook.id == getNotebookSelectedId() }" ng-drop="true" ng-drop-success="onDropSuccess($data,$event)"><i class="fa" ng-class="isCollectionOpen(notebook.id) ? 'fa-folder-open' : notebookIconByType(notebook.type)"></i> {{notebook.title}}</div>
                                        <ul class="tree-child tree-children" ng-class=" { 'hidden': !isCollectionOpen(notebook.id) } ">
                                            <li class="tree-notebook" ng-repeat="child in notebook.children | orderBy:'title'">
                                                <div class="notebook-title" ng-click="openNotebook(child.id, child.type, child.id)" ng-class="{ 'active': child.id == getNotebookSelectedId(), 'childNotebook': child.parent_id != NULL }"><i class="fa {{ notebookIconByType(child.type) }}"></i> {{child.title}}</div>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <span class="tree-header tree-header-tags" title="Click to {{ tagsCollapsed ? 'Expand' : 'Collapse' }}" ng-click="tagsCollapsed=!tagsCollapsed"><i class="fa {{ tagsCollapsed ? 'fa-chevron-right' : 'fa-chevron-down' }}"></i> Tags <button class="btn btn-default btn-xs pull-right" ng-click="modalManageTags();$event.stopPropagation();" title="Manage tags"><span class="fa fa-pencil"></span></button></span>
                                <ul class="tree-child" collapse="tagsCollapsed">
                                    <li class="tree-tag" ng-repeat="tag in tags | orderBy:'title':reverse" >
                                        <span class="tree-child tag-parent" ng-click="tag.collapsed=!tag.collapsed"><i class="fa {{ tag.collapsed ? 'fa-chevron-right' : 'fa-chevron-down' }}" ng-show="(tag.children.length > 0)"></i></span><div class="tree-child" ng-click="openTag(tag.id)" ng-class="{ 'active': tag.id == tagsSelectedId }" ng-drop="true" ng-drop-success="onDropToTag($data, $event)" ng-drag="(tag.children.length == 0)" ng-drag-success="onDragSuccess($data, $event)" ng-drag-data="tag"><i class="fa fa-tag" ng-show="(tag.children.length == 0)"></i> {{tag.title}}</div>
                                        <ul class="tree-child" collapse="tag.collapsed">
                                            <li class="tree-tag" ng-repeat="child in tag.children | orderBy:'title'" >
                                                <span ng-click="openTag(child.id)" ng-class="{ 'active': child.id == tagsSelectedId }" ng-drop="true" ng-drop-success="onDropToTag($data, $event)" ng-drag="true" ng-drag-success="onDragSuccess($data, $event)" ng-drag-data="child"><i class="fa fa-tag"></i> {{child.title}}</span>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <span class="tree-header tree-header-calendar" title="Click to {{ calendarCollapsed ? 'Expand' : 'Collapse' }}" ng-click="calendarCollapsed=!calendarCollapsed"><i class="fa {{ calendarCollapsed ? 'fa-chevron-right' : 'fa-chevron-down' }}"></i> Calendar</span>
                                <ul class="tree-child" collapse="calendarCollapsed">
                                    <li class="tree-calendar">
                                        <datepicker pw-datepicker-refresh="sidebarCalendarPromise" id="sidebarCalendar" date-disabled="sidebarCalendarIsDisabled(date, mode)" ng-change="openDate(sidebarCalendar)" ng-model="sidebarCalendar" show-weeks="false" ></datepicker>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </ul>
            </div>

            <div id="sidebarNotes" class="col-sm-4 col-md-3 sidebar hidden-xs animate-panel"
                 ng-controller="SidebarNotesController" ng-show="isVisible()" ng-class="sidebarCollapsed ? 'sidebar-collapsed-notes' : 'col-sm-offset-3 col-md-offset-2'" ng-if="(notes.length != 0)">
                <div class="nav nav-sidebar notes-list sidebar-no-border" ng-class="sidebarCollapsed ? 'sidebar-collapsed-notes-list' : ''" ng-cloak>
                    <p class="text-center">
                        Sort notes by:
                        <select id="sort_order_change" ng-change="changeSortOrder($root.sort_order_adjustment)" ng-model="$root.sort_order_adjustment">
                            <option value="default">Default</option>
                            <option value="creation_date">Creation Date</option>
                            <option value="modification_date">Modification Date</option>
                            <option value="title">Title</option>
                        </select>
                    </p>
                    <p class="text-center new-note-notes-list-button">
                        <a ng-controller="SidebarNotesController" ng-click="newNote(getNotebookSelectedId())" href><i class="fa fa-plus"></i> New note</a>
                    </p>
                </div>
                <ul id="notes-list" class="nav nav-sidebar notes-list sidebar-no-border" ng-controller="NotesListController" ng-class="sidebarCollapsed ? 'sidebar-collapsed-notes-list' : ''">
                    <li class="notes-list-item" ng-cloak ng-repeat="note in notes | orderBy : $root.sort_criteria[0] : $root.sort_criteria[1]"
                        ng-click="noteSelect(note.notebook_id, note.id)"
                        ng-dblclick="editNote(note.notebook_id, note.id)"
                        ng-class="{ 'active': (note.notebook_id + '-' + note.id == getNoteSelectedId() || (editMultipleNotes && notesSelectedIds[note.id])) }"
                        ng-drag="true"
                        ng-drag-data="(note)"
                        ng-drag-success="onDragSuccess($data,$event)"
                        data-allow-transform="false">
                        <span class="draggable"></span>
                        <div class="notes-list-item-checkbox col-sm-1" ng-show="editMultipleNotes">
                            <input name="notes[]" type="checkbox" value="{{ note.id }}" ng-model="notesSelectedIds[note.id]" ng-click="$event.stopPropagation();" ng-dblclick="$event.stopPropagation();">
                        </div>
                        <a class="{{ editMultipleNotes ? 'col-sm-11' : '' }}" href="#{{getNoteLink(note.notebook_id, note.id)}}">
                            <div class="">
                                <span class="notes-list-title notes-list-title-gradient">{{note.version.title || note.title}}</span>
                                <span class="notes-list-date">
    								<span class="notes-list-date-day">{{note.updated_at | convertdate | date : 'd'}}</span>
    								<span class="notes-list-date-month">{{note.updated_at | convertdate | date : 'MMM'}}</span>
    								<span class="notes-list-date-year">{{note.updated_at | convertdate | date : 'yyyy'}}</span>
    							</span>
                                <span class="notes-list-content notes-list-content-gradient" ng-bind-html="note.version.content_preview || note.content_preview"></span>
                            </div>
                        </a>
                        <div class="clear"></div>
                    </li>
                </ul>
            </div>
        </div>
        <div id="paperworkViewParent"
             class="main col-xs-12"
             ng-class="(getStyleClasses(notes.length, sidebarCollapsed))"
             ng-controller="ViewController">
            <div class="text-center"
                 id="paperworkViewEmpty"
                 ng-if="(notes.length == 0)"
                 ng-show="!expandedNoteLayout"
                 ng-class=""
                 ng-init=""
                 ng-cloak>
                <h1>Nothing here</h1>
                <p style="font-size:15px;padding-top:15px">Paperwork did not find any notes in this notebook. Go on and create a new one from the File menu. </p>
            </div>
            <div id="paperworkView" ng-view></div>
        </div>
    </div>
</div>


<div class="container-fluid">
    <div class="footer footer-issue " ondblclick="$(this).hide()">
        <div class="error-reporting">
            <div class="alert alert-warning" role="alert">
                <p>Found a bug? LavNote cannot connect to Github to check the latest version. A solution to this can be installling the curl Java extension. This is not mandatory, however before reporting any issues, please make sure that you are using the latest version.  </p>
                <p>Double click to dismiss.
            </div>
        </div>
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
