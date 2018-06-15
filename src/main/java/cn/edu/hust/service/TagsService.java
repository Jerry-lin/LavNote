package cn.edu.hust.service;

import cn.edu.hust.domain.Tags;

import java.util.List;

public interface TagsService {
    List<Tags> findListByUId(String id);

    List<Tags> findTagsListByids(List<String> tagIds);
}
