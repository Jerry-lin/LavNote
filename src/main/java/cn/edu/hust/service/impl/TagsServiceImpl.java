package cn.edu.hust.service.impl;

import cn.edu.hust.domain.Tags;
import cn.edu.hust.mapper.TagsMapper;
import cn.edu.hust.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagsMapper tagsMapper;
    @Override
    public List<Tags> findListByUId(String id) {
        return this.tagsMapper.findListByUId(id);
    }

    @Override
    public List<Tags> findTagsListByids(List<String> tagIds) {
        return this.tagsMapper.findTagsListByids(tagIds);
    }
}
