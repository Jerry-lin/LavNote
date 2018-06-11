package cn.edu.hust.domain;

public class Tag {
    private String TagId;
    private String UserId;
    private int Usn;
    private int Count;
    private String CreatedTime;
    private String UpdatedTime;
    private boolean IsDeleted;

    public void set(String tagId, String userId, int usn, int count, String createdTime, String updatedTime, boolean isDeleted) {
        TagId = tagId;
        UserId = userId;
        Usn = usn;
        Count = count;
        CreatedTime = createdTime;
        UpdatedTime = updatedTime;
        IsDeleted = isDeleted;
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getUsn() {
        return Usn;
    }

    public void setUsn(int usn) {
        Usn = usn;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
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

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}
