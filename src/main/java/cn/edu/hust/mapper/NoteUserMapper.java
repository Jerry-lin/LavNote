package cn.edu.hust.mapper;

import cn.edu.hust.domain.NoteUser;


public interface NoteUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteUser record);

    int insertSelective(NoteUser record);

    NoteUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteUser record);

    int updateByPrimaryKey(NoteUser record);
}