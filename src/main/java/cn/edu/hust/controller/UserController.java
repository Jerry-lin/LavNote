package cn.edu.hust.controller;

import cn.edu.hust.utils.domain.ResponseMsg;
import cn.edu.hust.domain.User;
import cn.edu.hust.service.UserService;
import cn.edu.hust.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(@RequestParam String username,@RequestParam String passwod)
    {
        return "";
    }

    /**
     * 1.将参数封装成bean
     * 2.传入service层到后台
     * 4.看是否用户名已经使用，如果使用告知用户
     * 3.向前端返回注册成功
     * @param username
     * @param pwd
     * @return
     */
    @RequestMapping("/register")
    public @ResponseBody ResponseMsg register(@RequestParam String username, @RequestParam String pwd)
    {
        User bean=this.userService.findUserByUserName(username);
        ResponseMsg<String> msg=new ResponseMsg();
        if(bean!=null)
        {
          msg.setSuccess(false);
          msg.setResponse("用户名已注册!");
          return msg;
        }
        User user=param2User(username, pwd);
        this.userService.insertUser(user);
        msg.setSuccess(true);
        return msg;
    }



    private User param2User(@RequestParam String username, @RequestParam String passwd) {
        User user=new User();
        String userId= UUID.randomUUID().toString().replace("-","");
        String password= CommonUtils.encoderByMD5(passwd);
        user.set(userId,username,"",password);
        return user;
    }
}
