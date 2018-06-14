package cn.edu.hust.mapper;

import cn.edu.hust.domain.Shortcuts;

public interface ShortcutsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Shortcuts record);

    int insertSelective(Shortcuts record);

    Shortcuts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Shortcuts record);

    int updateByPrimaryKey(Shortcuts record);
}