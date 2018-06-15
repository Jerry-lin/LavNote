package cn.edu.hust.mapper;

import cn.edu.hust.domain.NoteBooks;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoteBooksMapper {
    int deleteByPrimaryKey(String id);

    int insert(NoteBooks record);

    int insertSelective(NoteBooks record);

    NoteBooks selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NoteBooks record);

    int updateByPrimaryKey(NoteBooks record);

    List<NoteBooks> findALLNoteBooksByIds(@Param("noteBooksIds") List<String> noteBooksIds);
}