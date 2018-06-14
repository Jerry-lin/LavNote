package cn.edu.hust.mapper;

import cn.edu.hust.domain.Sessions;

public interface SessionsMapper {
    int insert(Sessions record);

    int insertSelective(Sessions record);
}