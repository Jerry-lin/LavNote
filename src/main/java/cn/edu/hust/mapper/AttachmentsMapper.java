package cn.edu.hust.mapper;

import cn.edu.hust.domain.Attachments;

public interface AttachmentsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Attachments record);

    int insertSelective(Attachments record);

    Attachments selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Attachments record);

    int updateByPrimaryKeyWithBLOBs(Attachments record);

    int updateByPrimaryKey(Attachments record);
}