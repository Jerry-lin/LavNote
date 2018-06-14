package cn.edu.hust.mapper;

import cn.edu.hust.domain.LanguageUser;

public interface LanguageUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LanguageUser record);

    int insertSelective(LanguageUser record);

    LanguageUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LanguageUser record);

    int updateByPrimaryKey(LanguageUser record);
}