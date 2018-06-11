package cn.edu.hust.controller.user;

import cn.edu.hust.domain.User;
import cn.edu.hust.service.note.NoteBooksService;
import cn.edu.hust.service.user.UserService;
import cn.edu.hust.utils.CommonUtils;
import cn.edu.hust.utils.Constant;
import cn.edu.hust.utils.PropertiesParser;
import cn.edu.hust.utils.bean.ResponseMsg;
import cn.edu.hust.service.user.jedis.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private NoteBooksService noteBooksSerice;
    @RequestMapping("/register")
    @ResponseBody
    public ResponseMsg register(@RequestParam String email, @RequestParam String pwd)
     {
        User user=param2User(email,pwd);
        return userService.register(user);
    }

    /**
     * 激活邮件
     * 如果没有注册，提示
     * 如果已经激活，警告请勿重复激活
     * 如果时间不超过48小时，那么激活，否则失败
     * @param token
     * @return
     */
    @RequestMapping("/activeEmail")
    public ModelAndView activeEmail(@RequestParam String token)
    {
        ModelAndView model=new ModelAndView();
        verifyEmail(token, model);
        return model;
    }


    /**
     * 忘记密码操作
     * 如果不存在邮箱，提示不存在
     * 如果存在，发送邮箱给这个邮箱
     * @param email
     */
    @RequestMapping("/doFindPassword")
    public @ResponseBody ResponseMsg doFindPassword(@RequestParam String email)
    {
        User user=this.userService.findUserByEmail(email);
        ResponseMsg responseMsg=new ResponseMsg();
        if(user==null)
        {
            responseMsg.setMsg(PropertiesParser.getEmailNOTExistsInfo());
            return  responseMsg;
        }
        this.userService.doFindPassword(email);
        responseMsg.setOk(true);
        responseMsg.setMsg(PropertiesParser.getResetEmailInfo());
        return responseMsg;
    }

    /**
     * 重置密码
     * @param pwd
     * @param token
     * @return
     */
    @RequestMapping("/findPasswordUpdate")
    public @ResponseBody ResponseMsg findPassword(@RequestParam String pwd,@RequestParam String token)
    {
        ResponseMsg msg=new ResponseMsg();
        String email=(String)redisUtils.get(token);
        if(email==null&& StringUtils.isBlank(email))
        {
            msg.setOk(false);
            msg.setMsg("该链接已经失效!");
        }
        User user=param2User(email,pwd);
        try
        {
            this.userService.findPassword(user);
            msg.setOk(true);
            //重置密码成功后
            redisUtils.remove(token);
        }
        catch(Exception e)
        {
            msg.setOk(false);
            msg.setMsg("重置密码失败名，请重试!");
        }
        return msg;
    }

    @RequestMapping("/doLogin")
    public @ResponseBody ResponseMsg doLogin(Model model, @RequestParam String pwd, @RequestParam String email)
    {
        ResponseMsg msg=new ResponseMsg();
        User user=param2User(email,pwd);
        User bean=this.userService.doLogin(user);
        if(bean==null)
        {
            msg.setOk(false);
            msg.setMsg("用户名或密码错误!");
        }
        else
        {
            //这里设置用户的session,默认设置为小时消失
            redisUtils.set(user.getEmail(),bean,30*60L);
            //model.addAttribute("mail",bean.getEmail());
            msg.setMsg(bean.getEmail());
            msg.setOk(true);
        }
        return msg;
    }

    @RequestMapping("/logout")
    public String logout(@RequestParam String email)
    {
        //将session移除即可
        redisUtils.remove(email);
        //跳转到登录页面
        return "home/index";
    }


    /**
     * 将参数封装成bean
     * @param email
     * @param pwd
     * @return
     */
    private User param2User(String email,String  pwd)
    {
        User user=new User();
        String time=new Timestamp(new Date().getTime()).toString();
        user.set(email, CommonUtils.encoderByMD5(pwd),time.substring(0,time.indexOf('.')));
        return user;
    }

    /**
     * 验证邮箱是否激活
     * @param token
     * @param model
     * @return
     */
    private boolean verifyEmail(String token, ModelAndView model) {
        String email=(String)redisUtils.get(token);
        //是否过期
        if(email==null&& StringUtils.isBlank(email))
        {
            model.addObject("title","验证邮箱 - 失败");
            model.addObject("status","您的邮箱  验证\n" +
                    "                失败");
            model.addObject("message","注册超过2天未激活,请重新注册!");
            this.userService.deleteByEmail(email);
            model.setViewName("user/activeEmail");
            return true;
        }
        User user=this.userService.findUserByEmail(email);
        if(user==null)
        {
            model.addObject("title","验证邮箱 - 失败");
            model.addObject("status","您的邮箱  验证\n" +
                    "                失败");
            model.addObject("message","邮箱还没有注册");
            model.setViewName("user/activeEmail");
            return true;
        }
        if(user.getStatus()==1)
        {
            model.addObject("title","验证邮箱 - 成功");
            model.addObject("status","您的邮箱  验证\n" +
                    "                成功");
            model.addObject("message","邮箱已经激活，请勿重复激活");
            model.setViewName("user/activeEmail");
            return true;
        }
        Long registerTime= CommonUtils.getTime(user.getCreateDate());
        if(new Date().getTime()-registerTime>48*60*60*1000)
        {

            model.addObject("title","验证邮箱 - 失败");
            model.addObject("status","您的邮箱  验证\n" +
                    "                失败");
            model.addObject("message","该链接已过期");
            model.setViewName("user/activeEmail");

        }
        this.userService.activeEmail(user);
        model.addObject("title","验证邮箱 - 成功");
        model.addObject("status","您的邮箱  验证\n" +
                "                成功");
        model.addObject("message","验证成功");
        model.setViewName("user/activeEmail");
        //激活成功后将session内容清空
        redisUtils.remove(token);
        //默认的插入
        String[] defaultTitle={"study","work","life"};
        for(String title:defaultTitle)
        {
            InsertNoteBooks(user,title);
        }
        return false;
    }

    private void InsertNoteBooks(User user,String title) {
        String NotebookId= UUID.randomUUID().toString().replace("-","");
        String UserId=user.getId()+"";
        String rowKey=UserId+"_"+NotebookId;
        String ParentNotebookId="";
        String Seq="-1";
        String Title=title;
        String UrlTitle="";
        String NumberNotes="1";
        String IsTrash="0";
        String IsBlog="0";
        String CreatedTime="0001-01-01T00:00:00Z";
        String UpdatedTime="001-01-01T00:00:00Z";
        String IsWX="0";
        String Usn="0";
        String IsDeleted="0";
        String Subs="";
        this.noteBooksSerice.addNoteBook(rowKey,
                Constant.NOTEBOOKS_NAME,
                new String[]{Constant.NOTEBOOKS_NAME_INFO1,Constant.NOTEBOOKS_NAME_INFO2},
                new String[][]{{"NotebookId","ParentNotebookId","Seq","Title","UrlTitle","NumberNotes"},{"IsTrash","IsBlog","CreatedTime","UpdatedTime","IsWX","Usn","IsDeleted","Subs"}},
                new String[][]{{NotebookId,ParentNotebookId,Seq,Title,UrlTitle,NumberNotes},{IsTrash,IsBlog,CreatedTime,UpdatedTime,IsWX,Usn,IsDeleted,Subs}});
    }

}
