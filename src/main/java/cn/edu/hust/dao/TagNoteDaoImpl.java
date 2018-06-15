package cn.edu.hust.dao;

import cn.edu.hust.domain.TagNote;
import cn.edu.hust.mapper.TagNoteMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagNoteDaoImpl implements TagNoteMapper{
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(TagNote record) {
        String sql="cn.edu.hust.mapper.TagNoteMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public int insertSelective(TagNote record) {
        return 0;
    }

    @Override
    public TagNote selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(TagNote record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(TagNote record) {
        return 0;
    }

    @Override
    public List<TagNote> findTagNoteListByNId(@Param("notesIds") List<String> notesIds) {
        String sql="cn.edu.hust.mapper.TagNoteMapper.findTagNoteListByNId";
        return this.sqlSessionFactory.openSession().selectList(sql,notesIds);
    }
}
