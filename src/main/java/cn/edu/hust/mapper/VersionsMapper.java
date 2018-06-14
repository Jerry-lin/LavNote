package cn.edu.hust.mapper;

import cn.edu.hust.domain.Versions;

public interface VersionsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Versions record);

    int insertSelective(Versions record);

    Versions selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Versions record);

    int updateByPrimaryKeyWithBLOBs(Versions record);

    int updateByPrimaryKey(Versions record);
}