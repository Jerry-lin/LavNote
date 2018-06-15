package cn.edu.hust.service.impl;

import cn.edu.hust.domain.TagNote;
import cn.edu.hust.mapper.TagNoteMapper;
import cn.edu.hust.service.TagNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagNoteServiceImpl implements TagNoteService{
    @Autowired
    private TagNoteMapper TagNoteMapper;
    @Override
    public List<TagNote> findTagNoteListByNId(List<String> notesIds) {
        return this.TagNoteMapper.findTagNoteListByNId(notesIds);
    }
}
