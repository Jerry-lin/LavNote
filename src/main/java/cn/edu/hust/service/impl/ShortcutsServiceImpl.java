package cn.edu.hust.service.impl;

import cn.edu.hust.domain.Shortcuts;
import cn.edu.hust.mapper.ShortcutsMapper;
import cn.edu.hust.service.ShortcutsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShortcutsServiceImpl implements ShortcutsService {

    @Autowired
    private ShortcutsMapper shortcutsMapper;
    @Override
    public List<Shortcuts> findShortcutsByUId(String uid) {
        return this.shortcutsMapper.findShortcutsByUId(uid);
    }
}
