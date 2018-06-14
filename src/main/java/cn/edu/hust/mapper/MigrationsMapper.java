package cn.edu.hust.mapper;

import cn.edu.hust.domain.Migrations;

public interface MigrationsMapper {
    int insert(Migrations record);

    int insertSelective(Migrations record);
}