package cn.edu.hust.mapper;

import cn.edu.hust.domain.Notes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotesMapper {
    int deleteByPrimaryKey(String id);

    int insert(Notes record);

    int insertSelective(Notes record);

    Notes selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Notes record);

    int updateByPrimaryKey(Notes record);

    List<Notes> findNotesListByNBId(@Param("noteBookIds") List<String> noteBookIds);
}