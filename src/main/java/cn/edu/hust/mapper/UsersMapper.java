package cn.edu.hust.mapper;

import cn.edu.hust.domain.Users;

import java.util.HashMap;

public interface UsersMapper {
    int deleteByPrimaryKey(String id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users findUsersByUserName(String username);

    Users findUserByUserAndPassword(HashMap<String, String> map);
}