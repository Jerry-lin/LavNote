package cn.edu.hust.mapper;

import cn.edu.hust.domain.User;

public interface UserMapper {

    int insert(User record);


    User findUserByUserName(String username);
}