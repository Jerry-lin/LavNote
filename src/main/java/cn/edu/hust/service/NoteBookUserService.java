package cn.edu.hust.service;

import cn.edu.hust.domain.NoteBookUser;

import java.util.List;

public interface NoteBookUserService {
    List<NoteBookUser> findNoteBookUserListByUID(String uid);
}
