package cn.edu.hust.mapper;

import cn.edu.hust.domain.NoteBookUser;

import java.util.List;

public interface NoteBookUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteBookUser record);

    int insertSelective(NoteBookUser record);

    NoteBookUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteBookUser record);

    int updateByPrimaryKey(NoteBookUser record);

    List<NoteBookUser> findNoteBookUserListByUID(String uid);
}