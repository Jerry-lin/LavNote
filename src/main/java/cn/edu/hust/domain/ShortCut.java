package cn.edu.hust.domain;

public class ShortCut {
    private String id;
    private String parent_id;
    private String shortcut_id;
    private int sortkey;
    private String title;
    private int type;

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

    public String getShortcut_id() {
        return shortcut_id;
    }

    public void setShortcut_id(String shortcut_id) {
        this.shortcut_id = shortcut_id;
    }

    public int getSortkey() {
        return sortkey;
    }

    public void setSortkey(int sortkey) {
        this.sortkey = sortkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
