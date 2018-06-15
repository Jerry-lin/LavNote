package cn.edu.hust.dao;

import cn.edu.hust.domain.Tags;
import cn.edu.hust.mapper.TagsMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagsDaoimpl implements TagsMapper{
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

    @Override
    public List<Tags> findListByUId(String id) {
        String sql="cn.edu.hust.mapper.TagsMapper.findListByUId";
        return this.sqlSessionFactory.openSession().selectList(sql,id);
    }

    @Override
    public List<Tags> findTagsListByids(@Param("tagIds")List<String> tagIds) {
        String sql="cn.edu.hust.mapper.TagsMapper.findTagsListByids";
        return this.sqlSessionFactory.openSession().selectList(sql,tagIds);
    }
}
