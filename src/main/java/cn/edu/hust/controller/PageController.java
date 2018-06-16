package cn.edu.hust.controller;

import cn.edu.hust.domain.Users;
import cn.edu.hust.service.jedis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 定位到主页
     * @return
     */
    @RequestMapping("/index")
    public String index()
    {
        return "home/main";
    }

    /**
     * 定位到注册
     * @return
     */
    @RequestMapping("/register")
    public String register()
    {
        return "user/regist";
    }
    /**
     * 定位到登录
     * @return
     */
    @RequestMapping("/login")
    public String login()
    {
        return "home/main";
    }
    
    @RequestMapping("/note")
    public String main()
    {
        //Users user=(Users)redisUtils.get(token);
        //model.addAttribute("user",user);
        return "note/main";
    }
}
