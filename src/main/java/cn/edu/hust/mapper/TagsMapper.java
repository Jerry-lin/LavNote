package cn.edu.hust.mapper;

import cn.edu.hust.domain.Tags;

public interface TagsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Tags record);

    int insertSelective(Tags record);

    Tags selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);
}