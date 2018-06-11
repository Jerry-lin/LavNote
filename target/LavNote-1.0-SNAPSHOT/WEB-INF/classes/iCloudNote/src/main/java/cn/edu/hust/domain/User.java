package cn.edu.hust.domain;

import java.io.Serializable;

public class User implements Serializable{
    private Integer id;

    private String email;

    private String passwd;

    private String imgpath;

    private String createDate;

    private Integer status;

    public void set(String email,String passwd,String createDate)
    {
        this.email=email;
        this.passwd=passwd;
        this.createDate=createDate;
    }

    public User(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath == null ? null : imgpath.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreatedate(String createdate) {
        this.createDate = createdate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}