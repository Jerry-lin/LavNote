package cn.edu.hust.service;

import cn.edu.hust.domain.*;

import java.util.HashMap;

public interface UsersService {
    Users findUsersByUserName(String username);

    void createUser(Users users, Settings settings, NoteBooks noteBooks, NoteBookUser noteBookUser, Versions versions, Notes notes, NoteUser noteUser, Tags tags, TagNote tagNote);

    Users findUserByUserAndPassword(HashMap<String, String> map);
}
