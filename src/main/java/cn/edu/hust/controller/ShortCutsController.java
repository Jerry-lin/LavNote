package cn.edu.hust.controller;

import cn.edu.hust.domain.Shortcuts;
import cn.edu.hust.domain.Users;
import cn.edu.hust.service.ShortcutsService;
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
import java.util.List;

@Controller
@RequestMapping("shortcuts")
public class ShortCutsController {
    @Autowired
    private RedisUtils redisUtils;

    private ShortcutsService shortcutsService;
    /**
     * 查询标签
     * 1.在从redis中取出users
     * 2.从redis中查询，如果有，那直接返回，否则进行第三步
     * 3.在数据库中查询，那么将结果放入redis中
     * @param request
     * @return
     */
    @RequestMapping("/showAllShortCuts")
    public @ResponseBody ResponseMsg<List<Shortcuts>> showAllShortCuts(HttpServletRequest request)
    {
        String key= CommonUtils.showCookies(request, Constant.COOKIE_NAME);
        Users users=(Users)redisUtils.get(key);
        ResponseMsg<List<Shortcuts>> msg=new ResponseMsg<>();
        msg.setSuccess(true);
        msg.setResponse(new ArrayList<Shortcuts>());
        Long size=redisUtils.lGetListSize(users.getId()+":"+Constant.SHORTCUTS_SUFFIX);
        if(size==0)
        {
            List<Shortcuts> shortcutsList=this.shortcutsService.findShortcutsByUId(users.getId());
            for(Shortcuts shortcuts:shortcutsList)
            {
                redisUtils.lSet(users.getId()+":"+Constant.SHORTCUTS_SUFFIX,shortcuts);
                msg.getResponse().add(shortcuts);
            }
        }
        else
        {
            List<Object> objects=redisUtils.lGet(users.getId()+":"+Constant.SHORTCUTS_SUFFIX,0,size);

            for(Object o:objects)
            {
                msg.getResponse().add((Shortcuts) o);
            }
        }
        return  msg;
    }
}
