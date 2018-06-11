package cn.edu.hust.service.note.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;

@Service
public class NoteSerivceImpl {
    @Autowired
    private HbaseTemplate hbaseTemplate;
}
