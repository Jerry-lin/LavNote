package cn.edu.hust.mapper;

import cn.edu.hust.domain.PasswordReminders;

public interface PasswordRemindersMapper {
    int insert(PasswordReminders record);

    int insertSelective(PasswordReminders record);
}