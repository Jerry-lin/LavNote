package cn.edu.hust.service.impl;

import cn.edu.hust.domain.Notes;
import cn.edu.hust.mapper.NotesMapper;
import cn.edu.hust.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesMapper notesMapper;
    @Override
    public List<Notes> findNotesListByNBId(List<String> noteBookIds) {
        return this.notesMapper.findNotesListByNBId(noteBookIds);
    }
}
