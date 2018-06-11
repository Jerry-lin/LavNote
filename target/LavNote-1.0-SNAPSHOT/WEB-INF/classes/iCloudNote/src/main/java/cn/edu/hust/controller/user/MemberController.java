package cn.edu.hust.controller.user;

import cn.edu.hust.domain.User;
import cn.edu.hust.service.user.UserService;
import cn.edu.hust.service.user.jedis.RedisUtils;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("member")
public class MemberController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;
    //展示个人中心
    @RequestMapping("/showMain")
    public String getMember(Model model, @RequestParam String email)
    {
        User user=(User)redisUtils.get(email);
        model.addAttribute("user",user);
        return "member/member";
    }

    @RequestMapping("/showUserName")
    public String StringshowUserName(Model model,@RequestParam String email)
    {
        User user=(User)redisUtils.get(email);
        model.addAttribute("user",user);
        return "member/membername";
    }


}
