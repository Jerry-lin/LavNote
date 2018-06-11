package cn.edu.hust.mapper;

import cn.edu.hust.domain.User;

public interface UserMapper{

    void insertUser(User record);

    User findUserByEmail(String email);

    void updateUserByEmail(String email);

    void updatePasswdByEmail(User user);

    User findUserByEmailAndPasswd(User user);

    void deleteByEmail(String email);
}