package cn.edu.hust.mapper;

import cn.edu.hust.domain.AttachmentVersion;

public interface AttachmentVersionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AttachmentVersion record);

    int insertSelective(AttachmentVersion record);

    AttachmentVersion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AttachmentVersion record);

    int updateByPrimaryKey(AttachmentVersion record);
}