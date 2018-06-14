package cn.edu.hust.dao;

import cn.edu.hust.domain.NoteBooks;
import cn.edu.hust.mapper.NoteBooksMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoteBooksDaoImpl implements NoteBooksMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(NoteBooks record) {
        String sql="cn.edu.hust.mapper.NoteBooksMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public int insertSelective(NoteBooks record) {
        return 0;
    }

    @Override
    public NoteBooks selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(NoteBooks record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(NoteBooks record) {
        return 0;
    }
}
