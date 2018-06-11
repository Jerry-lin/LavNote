package cn.edu.hust.controller;

import cn.edu.hust.domain.ResponseMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/login")
    public String login(@RequestParam String username,@RequestParam String passwod)
    {
        return "";
    }

    @RequestMapping("/register")
    public @ResponseBody
    ResponseMsg register(@RequestParam String email, @RequestParam String passwd)
    {

        return null;
    }
}
