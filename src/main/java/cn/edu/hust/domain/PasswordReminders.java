package cn.edu.hust.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class PasswordReminders implements Serializable{
    private String email;

    private String token;

    private Timestamp created_at=new Timestamp(System.currentTimeMillis());

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}