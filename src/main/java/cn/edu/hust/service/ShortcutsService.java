package cn.edu.hust.service;

import cn.edu.hust.domain.Shortcuts;

import java.util.List;

public interface ShortcutsService {
    List<Shortcuts> findShortcutsByUId(String id);
}
