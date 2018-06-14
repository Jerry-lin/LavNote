package cn.edu.hust.mapper;

import cn.edu.hust.domain.Settings;

public interface SettingsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Settings record);

    int insertSelective(Settings record);

    Settings selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Settings record);

    int updateByPrimaryKey(Settings record);
}