package cn.edu.hust.dao;

import cn.edu.hust.domain.NoteUser;
import cn.edu.hust.mapper.NoteUserMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoteUseDaoimpl implements NoteUserMapper{
    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(NoteUser record) {
        String sql="cn.edu.hust.mapper.NoteUserMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public int insertSelective(NoteUser record) {
        return 0;
    }

    @Override
    public NoteUser selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(NoteUser record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(NoteUser record) {
        return 0;
    }
}
