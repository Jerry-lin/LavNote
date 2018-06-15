package cn.edu.hust.service;

import cn.edu.hust.domain.NoteBooks;

import java.util.List;

public interface NoteBooksService {
    List<NoteBooks> findALLNoteBooksById(String id);

    List<NoteBooks> findALLNoteBooksByIds(List<String> noteBooksIds);
}
