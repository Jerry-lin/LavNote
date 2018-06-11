package cn.edu.hust.service.note;

import cn.edu.hust.domain.NoteBooks;

import java.util.List;

public interface NoteBooksService {
    public void addNoteBook(final String rowKey, final String tableName,final String[] family, final String[][] column, final String[][] value);

    List<NoteBooks> findByRowKey(final String key);
}
