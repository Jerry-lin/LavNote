package cn.edu.hust.mapper;

import cn.edu.hust.domain.NoteBookUser;

public interface NoteBookUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteBookUser record);

    int insertSelective(NoteBookUser record);

    NoteBookUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteBookUser record);

    int updateByPrimaryKey(NoteBookUser record);
}