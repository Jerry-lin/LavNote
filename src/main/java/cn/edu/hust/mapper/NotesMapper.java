package cn.edu.hust.mapper;

import cn.edu.hust.domain.Notes;

public interface NotesMapper {
    int deleteByPrimaryKey(String id);

    int insert(Notes record);

    int insertSelective(Notes record);

    Notes selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Notes record);

    int updateByPrimaryKey(Notes record);
}