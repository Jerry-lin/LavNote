package cn.edu.hust.utils.bean;

import cn.edu.hust.domain.NoteBooks;
import com.sun.tools.javac.jvm.Items;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ResponseMsg implements Serializable {
    private int id;
    private boolean ok=false;
    private String Msg;
    private String Item;
    private List<NoteBooks> noteBooks=new LinkedList<>();

    public ResponseMsg() {
    }

    public ResponseMsg(boolean ok, String msg) {
        this.ok = ok;
        Msg = msg;
    }

    public ResponseMsg(int id, boolean ok, String msg) {
        this.id = id;
        this.ok = ok;
        Msg = msg;
    }


    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public List<NoteBooks> getNoteBooks() {
        return noteBooks;
    }

    public void setNoteBooks(List<NoteBooks> noteBooks) {
        noteBooks = noteBooks;
    }
}
