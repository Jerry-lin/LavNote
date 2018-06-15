package cn.edu.hust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {
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
        return "note/main";
    }
}
