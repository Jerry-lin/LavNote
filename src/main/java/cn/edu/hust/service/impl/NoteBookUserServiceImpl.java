package cn.edu.hust.service.impl;

import cn.edu.hust.domain.NoteBookUser;
import cn.edu.hust.mapper.NoteBookUserMapper;
import cn.edu.hust.mapper.NoteUserMapper;
import cn.edu.hust.service.NoteBookUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteBookUserServiceImpl implements NoteBookUserService {

    @Autowired
    private NoteBookUserMapper noteBookUserMapper;
    @Override
    public List<NoteBookUser> findNoteBookUserListByUID(String uid) {
        return this.noteBookUserMapper.findNoteBookUserListByUID(uid);
    }
}
