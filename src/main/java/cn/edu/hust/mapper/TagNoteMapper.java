package cn.edu.hust.mapper;

import cn.edu.hust.domain.TagNote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagNoteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TagNote record);

    int insertSelective(TagNote record);

    TagNote selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TagNote record);

    int updateByPrimaryKey(TagNote record);

    List<TagNote> findTagNoteListByNId(@Param("notesIds") List<String> notesIds);
}