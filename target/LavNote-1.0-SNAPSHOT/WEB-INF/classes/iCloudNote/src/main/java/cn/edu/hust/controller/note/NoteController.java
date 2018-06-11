package cn.edu.hust.controller.note;

import cn.edu.hust.domain.NoteBooks;
import cn.edu.hust.domain.User;
import cn.edu.hust.service.note.NoteBooksService;
import cn.edu.hust.service.user.jedis.RedisUtils;
import cn.edu.hust.utils.bean.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private NoteBooksService noteBooksService;

    @RequestMapping("/showMain")
    public String showMain(Model model,@RequestParam String mail)
    {
        User user=(User)redisUtils.get(mail);
        model.addAttribute("user",user);
        return "note/main";
    }

    @RequestMapping("/listNoteBooks")
    public @ResponseBody ResponseMsg listNoteBooks(@RequestParam String email)
    {
        User user=(User)this.redisUtils.get(email);
        String key=user.getId()+"";
        List<NoteBooks> noteBooksList=this.noteBooksService.findByRowKey(key);
        ResponseMsg msg=new ResponseMsg();
        msg.setOk(true);
        if(noteBooksList==null) return msg;
        for(int i=0;i<noteBooksList.size();i++)
        {
            msg.getNoteBooks().add(noteBooksList.get(i));
        }
        return msg;
    }

}
