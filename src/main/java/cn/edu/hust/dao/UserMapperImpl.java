package cn.edu.hust.dao;

import cn.edu.hust.domain.User;
import cn.edu.hust.mapper.UserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserMapperImpl implements UserMapper {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @Override
    public int insert(User record) {
        String sql="cn.edu.hust.mapper.UserMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public User findUserByUserName(String username) {
        String sql="cn.edu.hust.mapper.UserMapper.findUserByUserName";
        return this.sqlSessionFactory.openSession().selectOne(sql,username);
    }


}
