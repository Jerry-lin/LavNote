package cn.edu.hust.service.impl;

import cn.edu.hust.domain.User;
import cn.edu.hust.mapper.UserMapper;
import cn.edu.hust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        this.userMapper.insert(user);
    }

    @Override
    public User findUserByUserName(String username) {
        return this.userMapper.findUserByUserName(username);
    }
}
