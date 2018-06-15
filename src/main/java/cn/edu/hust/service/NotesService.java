package cn.edu.hust.service;

import cn.edu.hust.domain.Notes;

import java.util.List;

public interface NotesService {

    List<Notes> findNotesListByNBId(List<String> noteBookIds);
}
