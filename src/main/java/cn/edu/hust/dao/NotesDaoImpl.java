package cn.edu.hust.dao;

import cn.edu.hust.domain.Notes;
import cn.edu.hust.mapper.NotesMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotesDaoImpl implements NotesMapper{
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Notes record) {
        String sql="cn.edu.hust.mapper.NotesMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public int insertSelective(Notes record) {
        return 0;
    }

    @Override
    public Notes selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Notes record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Notes record) {
        return 0;
    }

    @Override
    public List<Notes> findNotesListByNBId(@Param("noteBookIds") List<String> noteBookIds) {
        String sql="cn.edu.hust.mapper.NotesMapper.findNotesListByNBId";
        return this.sqlSessionFactory.openSession().selectList(sql,noteBookIds);
    }
}
