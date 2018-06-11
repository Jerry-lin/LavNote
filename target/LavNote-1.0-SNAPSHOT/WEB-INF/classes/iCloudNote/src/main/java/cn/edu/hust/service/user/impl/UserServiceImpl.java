package cn.edu.hust.service.user.impl;
import cn.edu.hust.domain.User;
import cn.edu.hust.mapper.UserMapper;
import cn.edu.hust.service.user.UserService;
import cn.edu.hust.service.user.jedis.RedisUtils;
import cn.edu.hust.utils.CommonUtils;
import cn.edu.hust.utils.PropertiesParser;
import cn.edu.hust.utils.bean.ResponseMsg;
import cn.edu.hust.utils.email.MailInfo;
import cn.edu.hust.utils.email.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.edu.hust.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired(required = false)
    private UserMapper userDao;

    @Autowired(required = false)
    private RedisUtils redisUtils;
    /**
     * 注册功能
     * 1.查看是否注册
     * 2.新增用户
     * 3.发送激活邮件
     * @param user
     */
    @Override
    public ResponseMsg register(User user) {
        /**
         * 查找是否注册
         */
        User bean=this.userDao.findUserByEmail(user.getEmail());
        ResponseMsg msg=new ResponseMsg();
        //如果邮件已经注册，那么将会返回信息
        if(bean!=null){
            msg.setMsg(PropertiesParser.getEmailExistsInfo());
            return msg;
        }
        //新增用户
        this.userDao.insertUser(user);
        msg.setOk(true);
        String token=UUID.randomUUID().toString().replaceAll("-","");
        //给用户保存48小时
        redisUtils.set(token,user.getEmail(),48*60*60L);
        String title=PropertiesParser.getEmailRegisterTitle();
        String content=user.getEmail()+"您好, 欢迎来到Fastnote.\n"+"请点击链接验证邮箱: https://localhost:8080/user/activeEmail.do?token="+token+"\n" +
                "\n" +
                "48小时后过期.";
        //新增用户成功后发送激活邮件
        MailInfo mail=str2Mail(user.getEmail(),title,content);
        MessageSender.sendMail(mail);
        return msg;
    }

    /**
     * 根据邮件查找用户
     * @param email
     * @return
     */
    @Override
    public User findUserByEmail(String email) {
        return this.userDao.findUserByEmail(email);
    }

    /**
     * 激活邮件
     * @param user
     */
    @Override
    public void activeEmail(User user) {
        this.userDao.updateUserByEmail(user.getEmail());
    }

    /**
     * 发送重置邮件
     * @param email
     */
    @Override
    public void doFindPassword(String email) {
        String title="找回密码";
        String token= UUID.randomUUID().toString().replaceAll("-","");
        redisUtils.set(token,email,48*60*60L);
        String content="请点击链接修改密码 https://localhost:8080/page/reset.do?token="+token+"\n" +
                "\n" +
                "48小时后过期.";
        //发送邮件
        MailInfo mail=str2Mail(email,title,content);
        MessageSender.sendMail(mail);
    }

    /**
     * 重置密码
     * @param user
     */
    @Override
    public void findPassword(User user) {
        String passwd=CommonUtils.encoderByMD5(user.getPasswd());
        user.setPasswd(passwd);
        this.userDao.updatePasswdByEmail(user);
    }

    /**
     * 登录功能
     * @param user
     * @return
     */
    @Override
    public User doLogin(User user) {
        String passwd=CommonUtils.encoderByMD5(user.getPasswd());
        user.setPasswd(passwd);
        return this.userDao.findUserByEmailAndPasswd(user);
    }

    @Override
    public void deleteByEmail(String email) {
        this.userDao.deleteByEmail(email);
    }


    private MailInfo str2Mail(String email,String title,String content)
    {


        List<String> receiver=new ArrayList<>();
        receiver.add(email);
        List<String> copySenders=new ArrayList<>();
        //配置抄送者
        String[] splits=PropertiesParser.getCopy2Send().split(",");
        for (int i=0;i<splits.length;i++)
        {
            copySenders.add(splits[i]);
        }
        MailInfo mail=new MailInfo(title,content,receiver,copySenders);
        return mail;
    }


}
