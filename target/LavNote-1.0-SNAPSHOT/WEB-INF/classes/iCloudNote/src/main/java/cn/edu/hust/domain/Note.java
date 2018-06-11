package cn.edu.hust.domain;

public class Note {
    private String UserId;
    private String NoteId;
    private String NoteBookId;
    private String Title;
    private String Desc;
    private String Src;
    private String ImgSrc;
    private String Tags;
    private boolean IsTrash;
    private boolean IsBlog;
    private String UrlTitle;
    private boolean IsRecommend;
    private boolean IsTop;
    private boolean HasSelfDefined;
    private int ReadNum;
    private int LikeNum;
    private int CommentNum;
    private boolean IsMarkdown;
    private int AttachNum;
    private String CreatedTime;
    private String UpdatedTime;
    private String RecommendTime;
    private String PublicTime;
    private String UpdatedUserId;
    private int Usn;
    private boolean IsDeleted;
    private boolean IsPublicShare;

    public void set(String userId, String noteId, String noteBookId, String title, String desc, String src, String imgSrc, String tags, boolean isTrash, boolean isBlog, String urlTitle, boolean isRecommend, boolean isTop, boolean hasSelfDefined, int readNum, int likeNum, int commentNum, boolean isMarkdown, int attachNum, String createdTime, String updatedTime, String recommendTime, String publicTime, String updatedUserId, int usn, boolean isDeleted, boolean isPublicShare) {
        UserId = userId;
        NoteId = noteId;
        NoteBookId = noteBookId;
        Title = title;
        Desc = desc;
        Src = src;
        ImgSrc = imgSrc;
        Tags = tags;
        IsTrash = isTrash;
        IsBlog = isBlog;
        UrlTitle = urlTitle;
        IsRecommend = isRecommend;
        IsTop = isTop;
        HasSelfDefined = hasSelfDefined;
        ReadNum = readNum;
        LikeNum = likeNum;
        CommentNum = commentNum;
        IsMarkdown = isMarkdown;
        AttachNum = attachNum;
        CreatedTime = createdTime;
        UpdatedTime = updatedTime;
        RecommendTime = recommendTime;
        PublicTime = publicTime;
        UpdatedUserId = updatedUserId;
        Usn = usn;
        IsDeleted = isDeleted;
        IsPublicShare = isPublicShare;
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

    public String getNoteBookId() {
        return NoteBookId;
    }

    public void setNoteBookId(String noteBookId) {
        NoteBookId = noteBookId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getSrc() {
        return Src;
    }

    public void setSrc(String src) {
        Src = src;
    }

    public String getImgSrc() {
        return ImgSrc;
    }

    public void setImgSrc(String imgSrc) {
        ImgSrc = imgSrc;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
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

    public String getUrlTitle() {
        return UrlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        UrlTitle = urlTitle;
    }

    public boolean isRecommend() {
        return IsRecommend;
    }

    public void setRecommend(boolean recommend) {
        IsRecommend = recommend;
    }

    public boolean isTop() {
        return IsTop;
    }

    public void setTop(boolean top) {
        IsTop = top;
    }

    public boolean isHasSelfDefined() {
        return HasSelfDefined;
    }

    public void setHasSelfDefined(boolean hasSelfDefined) {
        HasSelfDefined = hasSelfDefined;
    }

    public int getReadNum() {
        return ReadNum;
    }

    public void setReadNum(int readNum) {
        ReadNum = readNum;
    }

    public int getLikeNum() {
        return LikeNum;
    }

    public void setLikeNum(int likeNum) {
        LikeNum = likeNum;
    }

    public int getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(int commentNum) {
        CommentNum = commentNum;
    }

    public boolean isMarkdown() {
        return IsMarkdown;
    }

    public void setMarkdown(boolean markdown) {
        IsMarkdown = markdown;
    }

    public int getAttachNum() {
        return AttachNum;
    }

    public void setAttachNum(int attachNum) {
        AttachNum = attachNum;
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

    public String getRecommendTime() {
        return RecommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        RecommendTime = recommendTime;
    }

    public String getPublicTime() {
        return PublicTime;
    }

    public void setPublicTime(String publicTime) {
        PublicTime = publicTime;
    }

    public String getUpdatedUserId() {
        return UpdatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        UpdatedUserId = updatedUserId;
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

    public boolean isPublicShare() {
        return IsPublicShare;
    }

    public void setPublicShare(boolean publicShare) {
        IsPublicShare = publicShare;
    }
}
