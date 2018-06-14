package cn.edu.hust.dao;

import cn.edu.hust.domain.Tags;
import cn.edu.hust.mapper.TagsMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TagsDaoimpl implements TagsMapper {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Tags record) {
        String sql="cn.edu.hust.mapper.TagsMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public int insertSelective(Tags record) {
        return 0;
    }

    @Override
    public Tags selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Tags record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Tags record) {
        return 0;
    }
}
