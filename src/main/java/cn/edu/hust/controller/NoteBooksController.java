package cn.edu.hust.controller;

import cn.edu.hust.domain.NoteBookUser;
import cn.edu.hust.domain.NoteBooks;
import cn.edu.hust.domain.Users;
import cn.edu.hust.service.NoteBookUserService;
import cn.edu.hust.service.NoteBooksService;
import cn.edu.hust.service.jedis.RedisUtils;
import cn.edu.hust.utils.CommonUtils;
import cn.edu.hust.utils.Constant;
import cn.edu.hust.utils.domain.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/notebooks")
public class NoteBooksController {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private NoteBooksService noteBooksService;

    @Autowired
    private NoteBookUserService noteBookUserSerivce;

    /**
     * 1.先根据客户端的Cookie，看redis中是否有Session，如果没有那么将返回登录
     * 2.如果有session，根据userId查询redis中是否有,如果有，直接进入3，否则查询NoteBooks,将NoteBooks放入redis
     * 3.封装Json，返回
     * @return
     */
    @RequestMapping("/showAllNoteBooks")
    public @ResponseBody
    ResponseMsg<List<NoteBooks>> showAllNoteBooks(HttpServletRequest request)
    {
        String key=CommonUtils.showCookies(request, Constant.COOKIE_NAME);
        Object o=redisUtils.get(key);
        Users users=(Users) o;
        //2.查看redis中是否有相应的notebooks
        long size=redisUtils.lGetListSize(users.getId()+":"+Constant.NOTEBOOKS_SUFFIX);
        ResponseMsg msg=new ResponseMsg();
        msg.setSuccess(true);
        msg.setResponse(new ArrayList<NoteBooks>());
        //redis中没有缓存
        if(size==0)
        {
                List<NoteBookUser> noteBookUserList=this.noteBookUserSerivce.findNoteBookUserListByUID(users.getId());
                List<String> noteBooksIds=new ArrayList();
                for(NoteBookUser noteBookUser:noteBookUserList)
                {
                    noteBooksIds.add(noteBookUser.getNotebook_id());
                }
                List<NoteBooks> noteBooksList=this.noteBooksService.findALLNoteBooksByIds(noteBooksIds);
                //放入redis
                redisUtils.lSet(users.getId()+":"+Constant.NOTEBOOKUSER_SUFFIX,Constant.ROOT_NOTEBOOKS);
                for(int i=0;i<noteBookUserList.size();i++)
                {
                    redisUtils.lSet(users.getId()+":"+Constant.NOTEBOOKUSER_SUFFIX,noteBookUserList.get(i));
                }
                //封装json
               ((List<NoteBooks>)msg.getResponse()).add(Constant.ROOT_NOTEBOOKS);
                for (int i=0;i<noteBooksList.size();i++)
                {
                    ((List<NoteBooks>)msg.getResponse()).add(noteBooksList.get(i));

                    redisUtils.lSet(users.getId()+":"+Constant.NOTEBOOKS_SUFFIX,noteBooksList.get(i));
                }

        }
        else
        {
            //NoteBooks noteBooksArray=new NoteBooks[];
            List<Object> objects=redisUtils.lGet(users.getId()+":"+Constant.NOTEBOOKS_SUFFIX,0,size);
            for(int i=0;i<objects.size();i++)
            {
                NoteBooks noteBooks=(NoteBooks)(objects.get(i));
                ((List<NoteBooks>)msg.getResponse()).add(noteBooks);
            }
        }
        return msg;
    }
}
