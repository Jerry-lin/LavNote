package cn.edu.hust.mapper;

import cn.edu.hust.domain.Versions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VersionsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Versions record);

    int insertSelective(Versions record);

    Versions selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Versions record);

    int updateByPrimaryKeyWithBLOBs(Versions record);

    int updateByPrimaryKey(Versions record);

    List<Versions> findVersionsListByVIds(@Param("versionIds") List<String> versionIds);
}