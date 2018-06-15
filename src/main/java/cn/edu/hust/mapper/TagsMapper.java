package cn.edu.hust.mapper;

import cn.edu.hust.domain.Tags;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Tags record);

    int insertSelective(Tags record);

    Tags selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);

    List<Tags> findListByUId(String id);

    List<Tags> findTagsListByids(@Param("tagIds") List<String> tagIds);
}