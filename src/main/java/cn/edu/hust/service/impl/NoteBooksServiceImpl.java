package cn.edu.hust.service.impl;

import cn.edu.hust.domain.NoteBooks;
import cn.edu.hust.mapper.NoteBooksMapper;
import cn.edu.hust.service.NoteBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteBooksServiceImpl implements NoteBooksService{

    @Autowired
    private NoteBooksMapper noteBooksMapper;
    @Override
    public List<NoteBooks> findALLNoteBooksById(String id) {
        return null;
    }

    @Override
    public List<NoteBooks> findALLNoteBooksByIds(List<String> noteBooksIds) {
        return this.noteBooksMapper.findALLNoteBooksByIds(noteBooksIds);
    }
}
