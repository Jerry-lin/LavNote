package cn.edu.hust.dao;

import cn.edu.hust.domain.NoteBookUser;
import cn.edu.hust.mapper.NoteBookUserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteBookUserDaoImpl implements NoteBookUserMapper{
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(NoteBookUser record) {
        String sql="cn.edu.hust.mapper.NoteBookUserMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public int insertSelective(NoteBookUser record) {
        return 0;
    }

    @Override
    public NoteBookUser selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(NoteBookUser record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(NoteBookUser record) {
        return 0;
    }

    @Override
    public List<NoteBookUser> findNoteBookUserListByUID(String uid) {
        String sql="cn.edu.hust.mapper.NoteBookUserMapper.findNoteBookUserListByUID";
        return this.sqlSessionFactory.openSession().selectList(sql,uid);
    }
}
