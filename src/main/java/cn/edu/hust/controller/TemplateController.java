package cn.edu.hust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequestMapping("/templates")
public class TemplateController {

    @RequestMapping("paperworkNoteShow")
    public @ResponseBody String paperworkNoteShow()
    {
        String page= HtmlUtils.htmlEscape("<div ng-hide=\"note == null\">\n" +
                "    <nav class=\"navbar navbar-inverse\" role=\"navigation\">\n" +
                "        <div class=\"row\">\n" +
                "            <div class=\"collapse navbar-collapse\" id=\"navbar-paperwork-note-show\">\n" +
                "                <ul class=\"nav navbar-nav\" id=\"notebook-selector\">\n" +
                "                    <li>\n" +
                "                        <div class=\"btn-group\">\n" +
                "                            <button ng-controller=\"SidebarNotesController\" class=\"btn btn-default navbar-btn\"\n" +
                "                                    title=\"Move note\"\n" +
                "                                    ng-click=\"modalMoveNote(note.notebook_id, note.id)\"><i class=\"fa fa-book\"></i>\n" +
                "                                {{note.notebook_title}}\n" +
                "                            </button>\n" +
                "                        </div>\n" +
                "                    </li>\n" +
                "                </ul>\n" +
                "                <ul class=\"nav navbar-nav pull-right\" id=\"note-toolbar\">\n" +
                "                    <li>\n" +
                "                        <div class=\"btn-group\">\n" +
                "                            <button id=\"note-info\" class=\"btn btn-default navbar-btn\" data-toggle=\"popover\"\n" +
                "                                    data-placement=\"bottom\"\n" +
                "                                    title=\"Note info\"\n" +
                "                                    data-title=\"Note info\"\n" +
                "                                    data-content='\n" +
                "\t\t\t\t\t\t<div class=\"row\">\n" +
                "\t\t\t\t\t\t\t<div class=\"col-xs-3\"><b>Created</b></div>\n" +
                "\t\t\t\t\t\t\t<div class=\"col-xs-9\">{{ note.created_at }}</div>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"row\">\n" +
                "\t\t\t\t\t\t\t<div class=\"col-xs-3\"><b>by</b></div>\n" +
                "\t\t\t\t\t\t\t<div class=\"col-xs-9\">{{ note.users[0].firstname }} {{ note.users[0].lastname }}</div>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"row\">\n" +
                "\t\t\t\t\t\t\t<div class=\"col-xs-3\"><b>Updated</b></div>\n" +
                "\t\t\t\t\t\t\t<div class=\"col-xs-9\">{{ note.updated_at }}</div>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"row\">\n" +
                "\t\t\t\t\t\t\t<div class=\"col-xs-3\"><b>by</b></div>\n" +
                "\t\t\t\t\t\t\t<div class=\"col-xs-9\">{{ note.version.user.firstname }} {{ note.version.user.lastname }}</div>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t'><i class=\"fa fa-info-circle\"></i></button>\n" +
                "                            <button class=\"btn btn-default navbar-btn\" title=\"Note history\"\n" +
                "                                    data-toggle=\"freqselector\" data-target=\"#wayback-machine\"><i\n" +
                "                                        class=\"fa fa-history\"></i></button>\n" +
                "                            <button class=\"btn btn-default navbar-btn\" title=\"Edit note\"\n" +
                "                                    ng-controller=\"SidebarNotesController\"\n" +
                "                                    ng-click=\"editNote(note.notebook_id, note.id)\"><i class=\"fa fa-pencil\"></i></button>\n" +
                "                            <button class=\"btn btn-default navbar-btn\" title=\"Share\"\n" +
                "                                    ng-controller=\"SidebarNotesController\" ng-click=\"modalShareNote(note.notebook_id, note.id)\"><i\n" +
                "                                        class=\"fa fa-share-alt\"></i></button>\n" +
                "                        </div>\n" +
                "                    </li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "            <!-- /.navbar-collapse -->\n" +
                "        </div>\n" +
                "        <!-- /.container-fluid -->\n" +
                "    </nav>\n" +
                "    <div id=\"wayback-machine\" class=\"freqselector\">\n" +
                "        <div class=\"freqselector-fadeout-left freqselector-fadeout\" ng-controller=\"WaybackController\"></div>\n" +
                "        <div class=\"freqselector-fadeout-right freqselector-fadeout\"></div>\n" +
                "        <div class=\"freqselector-arrow-top\"></div>\n" +
                "        <div class=\"freqselector-arrow-bottom\"></div>\n" +
                "        <div class=\"freqselector-background\">\n" +
                "            <div class=\"freqselector-content\">\n" +
                "                <div class=\"freqselector-item freqselector-item-dummy\">\n" +
                "                    <div id=\"freqselector-item-0\" class=\"freqselector-item-snap\"></div>\n" +
                "                </div>\n" +
                "                <div class=\"freqselector-item freqselector-item-not-dummy\" ng-repeat=\"version in note.versions\">\n" +
                "                    <div id=\"freqselector-item-{{version.id}}\" class=\"freqselector-item-snap\"\n" +
                "                         data-itemid=\"{{version.id}}\" data-itemlatest=\"{{version.latest}}\"></div>\n" +
                "                    <div>\n" +
                "                        <div class=\"freqselector-item-title\">{{version.timestamp * 1000 | date:'yyyy-MM-dd'}}</div>\n" +
                "                        <div class=\"freqselector-item-subtitle\">{{version.timestamp * 1000 | date:'HH:mm'}}</div>\n" +
                "                        <div class=\"freqselector-item-subtitle\">{{version.username}}</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"freqselector-item freqselector-item-dummy\">\n" +
                "                    <div id=\"freqselector-item-999999\" class=\"freqselector-item-snap\"></div>\n" +
                "                </div>\n" +
                "                <div class=\"clear\"></div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-xs-12\">\n" +
                "            <div class=\"alert alert-success animate-fade\" role=\"alert\" ng-show=\"note.version > 0\">\n" +
                "                You are previewing an older version of this note.\n" +
                "            </div>\n" +
                "            <div class=\"page-header\">\n" +
                "                <h1>{{note.version.title}}</h1>\n" +
                "\n" +
                "                <div class=\"note-tags-bar\">\n" +
                "\t\t\t\t<span ng-repeat=\"tag in note.tags\" ng-click=\"openTag(tag.id)\"\n" +
                "                      class=\"label label-tag label-tag-{{ tag.visibility < 1 ? 'private' : 'public' }}\"><i\n" +
                "                            class=\"fa fa-tags\"></i> {{ tag.title }}</span>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "            <!-- <div class=\"page-content\" ng-bind-html=\"note.version.content\">\n" +
                "            </div>-->\n" +
                "            <div class=\"note-content\" pw-note-content content=\"note.version.content\"></div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div class=\"padding-twenty\">\n" +
                "\n" +
                "    <div class=\"file-uploader\">\n" +
                "        \n" +
                "        <table class=\"table table-striped\" ng-show=\"(uploader.queue.length > 0 || fileList.length > 0)\">\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th class=\"status-th\"></th>\n" +
                "                    <th>Name</th>\n" +
                "                                    </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "                <tr ng-repeat=\"item in fileList\">\n" +
                "                    <td class=\"status-td\">\n" +
                "                        <i ng-controller=\"FileUploadController\" class=\"fa {{ getFaClassFromMimetype(item.mimetype) }}\"></i>\n" +
                "                    </td>\n" +
                "                    <td><a href=\"{{'/api/v1/notebooks/' + getNotebookSelectedId() + '/notes/' + (getNoteSelectedId(true)).noteId + '/versions/' + (getVersionSelectedId(true)).versionId + '/attachments/' + item.id + '/raw' }}\" target=\"_blank\"><strong>{{ item.filename }}</strong></a></td>\n" +
                "                                    </tr>\n" +
                "                <tr ng-repeat=\"item in uploader.queue\">\n" +
                "                    <td class=\"status-td\">\n" +
                "                        <span ng-show=\"item.isSuccess\"><i class=\"fa fa-check\"></i></span>\n" +
                "                        <span ng-show=\"item.isCancel\"><i class=\"fa fa-times\"></i></i></span>\n" +
                "                        <span ng-show=\"item.isError\"><i class=\"fa fa-exclamation-triangle\"></i></i></span>\n" +
                "                        <span ng-show=\"item.isUploading\"><i class=\"fa fa-circle-o-notch fa-spin\"></i></span>\n" +
                "                    </td>\n" +
                "                    <td><strong>{{ item.file.name }}</strong></td>\n" +
                "                    <td ng-show=\"uploader.isHTML5\">\n" +
                "                        <div class=\"progress\" style=\"margin-bottom: 0;\">\n" +
                "                            <div class=\"progress-bar\" role=\"progressbar\" ng-style=\"{ 'width': item.progress + '%' }\"></div>\n" +
                "                        </div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "\n" +
                "        \n" +
                "    </div>\n" +
                "\n" +
                "</div>\n" +
                "</div>\n");
        return page;
    }



    @RequestMapping("paperwork404")
    public @ResponseBody  String paperwork404()
    {
        String page=HtmlUtils.htmlEscape("\n" +
                "<div class=\"error-content\">\n" +
                "<h1>Four, oh four.</h1>\n" +
                "<p>The requested item could not be found.</p>\n" +
                "<p><a href=\"javascript:history.back()\" title=\"Back\">Let's go back in time and try something else!</a></p>\n" +
                "</div>");
        return page;
    }

    @RequestMapping("paperworkNoteEdit")
    public @ResponseBody String paperworkNoteEdit()
    {
        String page=HtmlUtils.htmlEscape("\n" +
                "<div ng-controller=\"FileUploadController\" class=\"file-upload-wrapper\" uploader=\"uploader\" nv-file-drop=\"\"\n" +
                "     uploader=\"uploader\" filters=\"queueLimit, customFilter\" ng-init=\"maximumAttachmentsPerNote=10\">\n" +
                "    <nav class=\"navbar navbar-inverse navbar-edit\" role=\"navigation\">\n" +
                "        <div class=\"row\">\n" +
                "            <div class=\"col-xs-12\" id=\"navbar-paperwork-note-edit\">\n" +
                "                <ul class=\"nav navbar-nav navbar-edit\">\n" +
                "                    <li>\n" +
                "                        <div class=\"btn-group\">\n" +
                "                            <!-- \t\t\t      \t\t<button ng-controller=\"SidebarNotesController\" class=\"btn btn-default navbar-btn\"><i class=\"fa fa-book\"></i> {{templateNoteEdit.notebook_title}}</button>\n" +
                "                                                      <button class=\"btn btn-default navbar-btn\"><i class=\"fa fa-tags\"></i></button> -->\n" +
                "                        </div>\n" +
                "                    </li>\n" +
                "                </ul>\n" +
                "                <ul class=\"nav navbar-nav pull-right navbar-edit\">\n" +
                "                    <li>\n" +
                "                        <div class=\"btn-group\" ng-controller=\"SidebarNotesController\" ng-init=\"removeEditorButtonsCKEditor='Cut,Copy,Paste,Undo,Redo,Anchor,Underline,Strike,Subscript,Superscript'\">\n" +
                "                            <a id=\"updateNote\" href=\"\" ng-click=\"updateNote()\" class=\"btn btn-default navbar-btn\"\n" +
                "                               title=\"Save\"><i class=\"fa fa-floppy-o\"></i></a>\n" +
                "                            <a href=\"\" ng-click=\"closeNote()\" class=\"btn btn-default navbar-btn\"\n" +
                "                               title=\"Close\"><i class=\"fa fa-times-circle\"></i></a>\n" +
                "                        </div>\n" +
                "                    </li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "            <!-- /.navbar-collapse -->\n" +
                "        </div>\n" +
                "        <!-- /.container-fluid -->\n" +
                "    </nav>\n" +
                "\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-9 col-sm-12\">\n" +
                "            <form role=\"form\" class=\"form\">\n" +
                "                <div>\n" +
                "                    <div class=\"page-header\">\n" +
                "                        <div class=\"form-group {{ errors.title ? 'has-error' : '' }}\">\n" +
                "                            <input type=\"text\" class=\"form-control input-lg\" id=\"title\"\n" +
                "                                   placeholder=\"Note title\"\n" +
                "                                   ng-model=\"templateNoteEdit.version.title\">\n" +
                "                        </div>\n" +
                "                        <div class=\"form-group {{ errors.tags ? 'has-error' : '' }}\">\n" +
                "                            <input type=\"text\" class=\"form-control input-lg\" id=\"tags\"\n" +
                "                                   placeholder=\"Tags, separated by “,” (comma)\">\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"page-content\">\n" +
                "                        <textarea id=\"content\" class=\"form-control\" rows=\"16\"\n" +
                "                                  ng-model=\"templateNoteEdit.version.content\"></textarea>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </form>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-3 col-sm-12\">\n" +
                "            <div class=\"padding-twenty\">\n" +
                "\n" +
                "    <div class=\"file-uploader\">\n" +
                "                <div ng-show=\"uploader.isHTML5\" id=\"file-upload-dropzone\" class=\"file-upload-dropzone\" over-class=\"file-upload-dropzone-active\" nv-file-over=\"\" uploader=\"uploader\">\n" +
                "            <span><i class=\"fa fa-upload\"></i> Upload document...</span>\n" +
                "        </div>\n" +
                "        <input id=\"file-upload-input\" class=\"{{ uploader.isHTML5 ? 'file-upload-input-hidden' : '' }}\" type=\"file\" nv-file-select=\"\" uploader=\"uploader\" multiple=\"\">\n" +
                "        \n" +
                "        <table class=\"table table-striped\" ng-show=\"(uploader.queue.length > 0 || fileList.length > 0)\">\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th class=\"status-th\"></th>\n" +
                "                    <th>Name</th>\n" +
                "                                        <th ng-show=\"uploader.isHTML5\"></th>\n" +
                "                                    </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "                <tr ng-repeat=\"item in fileList\">\n" +
                "                    <td class=\"status-td\">\n" +
                "                        <i ng-controller=\"FileUploadController\" class=\"fa {{ getFaClassFromMimetype(item.mimetype) }}\"></i>\n" +
                "                    </td>\n" +
                "                    <td><a href=\"{{'/api/v1/notebooks/' + getNotebookSelectedId() + '/notes/' + (getNoteSelectedId(true)).noteId + '/versions/' + (getVersionSelectedId(true)).versionId + '/attachments/' + item.id + '/raw' }}\" target=\"_blank\"><strong>{{ item.filename }}</strong></a></td>\n" +
                "                                        <td>\n" +
                "                        <a class=\"btn btn-default btn-xs btn-block\" ng-click=\"fileUploadInsertFile(getNotebookSelectedId(), (getNoteSelectedId(true)).noteId, (getVersionSelectedId(true)).versionId, item.id, item)\">Insert</a>\n" +
                "\n" +
                "                        <a class=\"btn btn-danger btn-xs btn-block\" ng-click=\"fileUploadDeleteFile(getNotebookSelectedId(), (getNoteSelectedId(true)).noteId, (getVersionSelectedId(true)).versionId, item.id)\">Delete</a>\n" +
                "                    </td>\n" +
                "                                    </tr>\n" +
                "                <tr ng-repeat=\"item in uploader.queue\">\n" +
                "                    <td class=\"status-td\">\n" +
                "                        <span ng-show=\"item.isSuccess\"><i class=\"fa fa-check\"></i></span>\n" +
                "                        <span ng-show=\"item.isCancel\"><i class=\"fa fa-times\"></i></i></span>\n" +
                "                        <span ng-show=\"item.isError\"><i class=\"fa fa-exclamation-triangle\"></i></i></span>\n" +
                "                        <span ng-show=\"item.isUploading\"><i class=\"fa fa-circle-o-notch fa-spin\"></i></span>\n" +
                "                    </td>\n" +
                "                    <td><strong>{{ item.file.name }}</strong></td>\n" +
                "                    <td ng-show=\"uploader.isHTML5\">\n" +
                "                        <div class=\"progress\" style=\"margin-bottom: 0;\">\n" +
                "                            <div class=\"progress-bar\" role=\"progressbar\" ng-style=\"{ 'width': item.progress + '%' }\"></div>\n" +
                "                        </div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "\n" +
                "                <div class=\"text-align-right\">\n" +
                "            <button type=\"button\" class=\"btn btn-success\" ng-click=\"uploader.uploadAll()\" ng-hide=\"uploader.getNotUploadedItems().length < 1 || uploader.isUploading\">\n" +
                "                <i class=\"fa fa-upload\"></i> Upload\n" +
                "            </button>\n" +
                "            <button type=\"button\" class=\"btn btn-warning\" ng-click=\"uploader.cancelAll()\" ng-hide=\"!uploader.isUploading\">\n" +
                "                <i class=\"fa fa-times\"></i> Cancel\n" +
                "            </button>\n" +
                "            <button type=\"button\" class=\"btn btn-danger\" ng-click=\"uploader.clearQueue()\" ng-hide=\"!uploader.queue.length || uploader.isUploading\">\n" +
                "                <i class=\"fa fa-trash-o\"></i> Remove\n" +
                "            </button>\n" +
                "        </div>\n" +
                "        \n" +
                "    </div>\n" +
                "\n" +
                "</div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n");
        return  page;
    }
}
