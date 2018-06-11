package cn.edu.hust.service.user;

import cn.edu.hust.domain.User;
import cn.edu.hust.utils.bean.ResponseMsg;

public interface UserService {
    ResponseMsg register(User user);
    User findUserByEmail(String email);

    void activeEmail(User user);

    void doFindPassword(String email);

    void findPassword(User user);

    User doLogin(User user);

    void deleteByEmail(String email);
}
