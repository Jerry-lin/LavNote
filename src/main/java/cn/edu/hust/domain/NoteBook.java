package cn.edu.hust.domain;

import java.util.LinkedList;
import java.util.List;

public class NoteBook {
    private List<NoteBook> children=new LinkedList<NoteBook>();
    private String created_at;
    private String id;
    private String parent_id;
    private String title;
    //0表示 1表示根 Collection    2表示所有的ALL Notes
    private String type;
    //父节点和子节点是一个子网掩码
    private int umask;
    private String updated_at;

    public List<NoteBook> getChildren() {
        return children;
    }

    public void setChildren(List<NoteBook> children) {
        this.children = children;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUmask() {
        return umask;
    }

    public void setUmask(int umask) {
        this.umask = umask;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
