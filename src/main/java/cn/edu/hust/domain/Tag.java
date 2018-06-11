package cn.edu.hust.domain;


import java.util.LinkedList;
import java.util.List;

public class Tag {

    private List<Tag> children=new LinkedList<Tag>();
    private String created_at;
    private String delete_at;
    private String id;
    private String parent_id;
    private String title;
    private String updated_at;
    private String user_id;
    private int visibility;

    public void set(List<Tag> children, String created_at, String delete_at, String id, String parent_id, String title, String updated_at, String user_id, int visibility) {
        this.children = children;
        this.created_at = created_at;
        this.delete_at = delete_at;
        this.id = id;
        this.parent_id = parent_id;
        this.title = title;
        this.updated_at = updated_at;
        this.user_id = user_id;
        this.visibility = visibility;
    }

    public List<Tag> getChildren() {
        return children;
    }

    public void setChildren(List<Tag> children) {
        this.children = children;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDelete_at() {
        return delete_at;
    }

    public void setDelete_at(String delete_at) {
        this.delete_at = delete_at;
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

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}
