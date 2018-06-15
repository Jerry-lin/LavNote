package cn.edu.hust.service.impl;

import cn.edu.hust.domain.Versions;
import cn.edu.hust.mapper.VersionsMapper;
import cn.edu.hust.service.VersionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VersionServiceImpl implements VersionsService {
    @Autowired
    private VersionsMapper versionsMapper;
    @Override
    public List<Versions> findVersionsListByVIds(List<String> versionIds) {
        return this.versionsMapper.findVersionsListByVIds(versionIds);
    }
}
