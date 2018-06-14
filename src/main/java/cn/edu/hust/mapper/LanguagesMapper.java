package cn.edu.hust.mapper;

import cn.edu.hust.domain.Languages;

public interface LanguagesMapper {
    int deleteByPrimaryKey(String id);

    int insert(Languages record);

    int insertSelective(Languages record);

    Languages selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Languages record);

    int updateByPrimaryKey(Languages record);
}