package cn.edu.hust.mapper;

import cn.edu.hust.domain.Shortcuts;

import java.util.List;

public interface ShortcutsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Shortcuts record);

    int insertSelective(Shortcuts record);

    Shortcuts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Shortcuts record);

    int updateByPrimaryKey(Shortcuts record);

    List<Shortcuts> findShortcutsByUId(String uid);
}