package cn.edu.hust.domain;

public class NoteContent {
    private String UserId;
    private String NoteId;
    private boolean IsBlog;
    private String Content;
    private String CreatedTime;
    private String UpdatedTime;
    private String UpdatedUserId;


    public void set(String userId, String noteId, boolean isBlog, String content, String createdTime, String updatedTime, String updatedUserId) {
        UserId = userId;
        NoteId = noteId;
        IsBlog = isBlog;
        Content = content;
        CreatedTime = createdTime;
        UpdatedTime = updatedTime;
        UpdatedUserId = updatedUserId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNoteId() {
        return NoteId;
    }

    public void setNoteId(String noteId) {
        NoteId = noteId;
    }

    public boolean isBlog() {
        return IsBlog;
    }

    public void setBlog(boolean blog) {
        IsBlog = blog;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        UpdatedTime = updatedTime;
    }

    public String getUpdatedUserId() {
        return UpdatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        UpdatedUserId = updatedUserId;
    }
}
