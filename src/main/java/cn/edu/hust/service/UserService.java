package cn.edu.hust.service;

import cn.edu.hust.domain.User;

public interface UserService {
    void insertUser(User user);

    User findUserByUserName(String username);
}
