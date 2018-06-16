package cn.edu.hust.dao;

import cn.edu.hust.domain.Shortcuts;
import cn.edu.hust.mapper.ShortcutsMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ShortcutsDaoImpl implements ShortcutsMapper {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Shortcuts record) {
        return 0;
    }

    @Override
    public int insertSelective(Shortcuts record) {
        return 0;
    }

    @Override
    public Shortcuts selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Shortcuts record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Shortcuts record) {
        return 0;
    }

    @Override
    public List<Shortcuts> findShortcutsByUId(String uid) {
        String sql="cn.edu.hust.mapper.ShortcutsMapper.findShortcutsByUId";
        return this.sqlSessionFactory.openSession().selectList(sql,uid);
    }
}
