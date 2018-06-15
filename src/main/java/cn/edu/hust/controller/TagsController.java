package cn.edu.hust.controller;

import cn.edu.hust.domain.Tags;
import cn.edu.hust.domain.Users;
import cn.edu.hust.service.jedis.RedisUtils;
import cn.edu.hust.utils.CommonUtils;
import cn.edu.hust.utils.Constant;
import cn.edu.hust.utils.domain.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.edu.hust.service.TagsService;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TagsService TagsService;
    /**
     * 查询用户所有的tags
     * @return
     */
    @RequestMapping("/showAllTags")
    public @ResponseBody
    ResponseMsg<List<Tags>> showAllTags(HttpServletRequest request)
    {
       String key= CommonUtils.showCookies(request, Constant.COOKIE_NAME);
       Users users=(Users)redisUtils.get(key);
       ResponseMsg<List<Tags>> msg=new ResponseMsg<>();
       msg.setSuccess(true);
       msg.setResponse(new ArrayList<Tags>());
       long size=redisUtils.lGetListSize(users.getId()+":"+Constant.TAGS_SUFFIX);
       //判断缓存里面有没有tags，如果没有访问数据库
       if(size==0)
       {
           List<Tags> tagsList=this.TagsService.findListByUId(users.getId());
           for(Tags tags:tagsList)
           {
               redisUtils.lSet(users.getId()+":"+Constant.TAGS_SUFFIX,tags);
               msg.getResponse().add(tags);
           }
       }
       else
       {
          List<Object> objects=redisUtils.lGet(users.getId()+":"+Constant.TAGS_SUFFIX,0,size);
          for (Object o:objects)
          {
              msg.getResponse().add((Tags) o);
          }
       }
       return msg;
    }
}
