package cn.edu.hust.dao;

import cn.edu.hust.domain.Settings;
import cn.edu.hust.mapper.SettingsMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsDaoImpl implements SettingsMapper{

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Settings record) {
        String sql="cn.edu.hust.mapper.SettingsMapper.insert";
        return this.sqlSessionFactory.openSession().insert(sql,record);
    }

    @Override
    public int insertSelective(Settings record) {
        return 0;
    }

    @Override
    public Settings selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Settings record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Settings record) {
        return 0;
    }
}
