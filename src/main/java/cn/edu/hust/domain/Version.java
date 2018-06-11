package cn.edu.hust.domain;

public class Version {
    private String content;
    private String content_preview;
    private String id;
    private String title;
    private User user;
    private String user_id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_preview() {
        return content_preview;
    }

    public void setContent_preview(String content_preview) {
        this.content_preview = content_preview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
