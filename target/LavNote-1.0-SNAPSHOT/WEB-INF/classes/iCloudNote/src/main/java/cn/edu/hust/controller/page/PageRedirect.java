package cn.edu.hust.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/page")
public class PageRedirect {

    @RequestMapping("/register")
    public String getRegistion()
    {
        return "user/register";
    }

    @RequestMapping("/index")
    public String getIndex(){
        return "home/index";
    }

    @RequestMapping("/login")
    public String getLogin(Model model,String email)
    {
        model.addAttribute("email",email);
        return "user/login";
    }

    @RequestMapping("/findPassword")
    public String findPassword()
    {
        return "user/findPassword";
    }

    @RequestMapping("/reset")
    public String reset(Model model, @RequestParam  String token){
        model.addAttribute("token",token);
        return "user/reset";
    }

    @RequestMapping("/main")
    public String forwardMain()
    {
        return "note/main";
    }
}
