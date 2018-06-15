package cn.edu.hust.service;

import cn.edu.hust.domain.Versions;

import java.util.List;

public interface VersionsService {
    List<Versions> findVersionsListByVIds(List<String> versionIds);
}
