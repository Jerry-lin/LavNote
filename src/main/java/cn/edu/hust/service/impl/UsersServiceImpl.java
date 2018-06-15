package cn.edu.hust.service.impl;

import cn.edu.hust.domain.*;
import cn.edu.hust.domain.NoteUser;
import cn.edu.hust.mapper.*;
import cn.edu.hust.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UsersServiceImpl implements UsersService{
    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private SettingsMapper settingsMapper;

    @Autowired
    private NoteBooksMapper noteBooksMapper;

    @Autowired
    private NoteBookUserMapper noteBookUserMapper;

    @Autowired
    private VersionsMapper versionsMapper;

    @Autowired
    private NotesMapper notesMapper;

    @Autowired
    private NoteUserMapper noteUserMapper;

    @Autowired
    private TagsMapper tagsMapper;

    @Autowired
    private TagNoteMapper tagNoteMapper;

    @Override
    public Users findUsersByUserName(String username) {

        return this.usersMapper.findUsersByUserName(username);
    }

    @Override
    public void createUser(Users users, Settings settings, NoteBooks noteBooks,
                           NoteBookUser noteBookUser, Versions versions,
                           Notes notes, NoteUser noteUser, Tags tags, TagNote tagNote) {
        this.usersMapper.insert(users);

        this.settingsMapper.insert(settings);

        this.noteBooksMapper.insert(noteBooks);

        this.noteBookUserMapper.insert(noteBookUser);

        this.versionsMapper.insert(versions);

        this.notesMapper.insert(notes);

        this.noteUserMapper.insert(noteUser);

        this.tagsMapper.insert(tags);

        this.tagNoteMapper.insert(tagNote);

    }

    @Override
    public Users findUserByUserAndPassword(HashMap<String, String> map) {
        return this.usersMapper.findUserByUserAndPassword(map);
    }


}
