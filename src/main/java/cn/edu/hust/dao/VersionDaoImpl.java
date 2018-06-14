package cn.edu.hust.dao;

import cn.edu.hust.domain.Versions;
import cn.edu.hust.mapper.VersionsMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Version;

@Repository
public class VersionDaoImpl implements VersionsMapper{
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Versions record) {
        String sql="cn.edu.hust.mapper.VersionsMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public int insertSelective(Versions record) {
        return 0;
    }

    @Override
    public Versions selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Versions record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeyWithBLOBs(Versions record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Versions record) {
        return 0;
    }
}
