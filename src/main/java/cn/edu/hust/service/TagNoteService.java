package cn.edu.hust.service;

import cn.edu.hust.domain.TagNote;

import java.util.List;

public interface TagNoteService {
    List<TagNote> findTagNoteListByNId(List<String> notesIds);
}
