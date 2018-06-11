package cn.edu.hust.domain;

import java.util.List;

public class NoteBooks {
    private String UserId;
    private String NotebookId;
    private String ParentNotebookId;
    private int Seq;
    private String Title;
    private String UrlTitle;
    private int NumberNotes;
    private boolean IsTrash;
    private boolean IsBlog;
    private String CreatedTime;
    private String UpdatedTime;
    private boolean IsWX;
    private int Usn;
    private boolean IsDeleted;
    private List<String> Subs;

    public void set(String userId, String notebookId, String parentNotebookId, int seq, String title, String urlTitle, int numberNotes, boolean isTrash, boolean isBlog, String createdTime, String updatedTime, boolean isWX, int usn, boolean isDeleted, List<String> subs) {
        UserId = userId;
        NotebookId = notebookId;
        ParentNotebookId = parentNotebookId;
        Seq = seq;
        Title = title;
        UrlTitle = urlTitle;
        NumberNotes = numberNotes;
        IsTrash = isTrash;
        IsBlog = isBlog;
        CreatedTime = createdTime;
        UpdatedTime = updatedTime;
        IsWX = isWX;
        Usn = usn;
        IsDeleted = isDeleted;
        Subs = subs;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getNotebookId() {
        return NotebookId;
    }

    public void setNotebookId(String notebookId) {
        NotebookId = notebookId;
    }

    public String getParentNotebookId() {
        return ParentNotebookId;
    }

    public void setParentNotebookId(String parentNotebookId) {
        ParentNotebookId = parentNotebookId;
    }

    public int getSeq() {
        return Seq;
    }

    public void setSeq(int seq) {
        Seq = seq;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrlTitle() {
        return UrlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        UrlTitle = urlTitle;
    }

    public int getNumberNotes() {
        return NumberNotes;
    }

    public void setNumberNotes(int numberNotes) {
        NumberNotes = numberNotes;
    }

    public boolean isTrash() {
        return IsTrash;
    }

    public void setTrash(boolean trash) {
        IsTrash = trash;
    }

    public boolean isBlog() {
        return IsBlog;
    }

    public void setBlog(boolean blog) {
        IsBlog = blog;
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

    public boolean isWX() {
        return IsWX;
    }

    public void setWX(boolean WX) {
        IsWX = WX;
    }

    public int getUsn() {
        return Usn;
    }

    public void setUsn(int usn) {
        Usn = usn;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public List<String> getSubs() {
        return Subs;
    }

    public void setSubs(List<String> subs) {
        Subs = subs;
    }

    @Override
    public String toString() {
        return "NoteBooks{" +
                "UserId='" + UserId + '\'' +
                ", NotebookId='" + NotebookId + '\'' +
                ", ParentNotebookId='" + ParentNotebookId + '\'' +
                ", Seq=" + Seq +
                ", Title='" + Title + '\'' +
                ", UrlTitle='" + UrlTitle + '\'' +
                ", NumberNotes=" + NumberNotes +
                ", IsTrash=" + IsTrash +
                ", IsBlog=" + IsBlog +
                ", CreatedTime='" + CreatedTime + '\'' +
                ", UpdatedTime='" + UpdatedTime + '\'' +
                ", IsWX=" + IsWX +
                ", Usn=" + Usn +
                ", IsDeleted=" + IsDeleted +
                ", Subs=" + Subs +
                '}';
    }
}
