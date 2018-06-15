package cn.edu.hust.utils;

import cn.edu.hust.domain.NoteBooks;

public class Constant {

    public static final String NOTEBOOKS_SUFFIX="notebooks";

    public static final String NOTEBOOKUSER_SUFFIX="notebookUser";

    public static final String TAGS_SUFFIX="tags";

    public static final String TAGNOTE_SUFFIX="tagnote";

    public static final String COOKIE_NAME="laravel_session";

    public static final NoteBooks ROOT_NOTEBOOKS=new NoteBooks("00000000-0000-0000-0000-000000000000",null,Byte.valueOf("2"),"All Notes");

    public static final String NOTES_SUFFIX ="notes";

    public static final String VERSION_SUFFIX ="version";
}
