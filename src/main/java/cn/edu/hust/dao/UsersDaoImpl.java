package cn.edu.hust.dao;

import cn.edu.hust.domain.Users;
import cn.edu.hust.mapper.UsersMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDaoImpl implements UsersMapper{
    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Users record) {
        String sql="cn.edu.hust.mapper.UsersMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public int insertSelective(Users record) {
        return 0;
    }

    @Override
    public Users selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Users record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Users record) {
        return 0;
    }

    @Override
    public Users findUsersByUserName(String username) {
        String sql="cn.edu.hust.mapper.UsersMapper.findUsersByUserName";
        return this.sqlSessionFactory.openSession().selectOne(sql,username);
    }
}
