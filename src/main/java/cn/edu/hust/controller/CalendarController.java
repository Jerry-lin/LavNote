package cn.edu.hust.controller;

import cn.edu.hust.service.jedis.RedisUtils;
import cn.edu.hust.utils.domain.ResponseMsg;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 日期json开发
     * @param request
     * @return
     */

    /*
    @RequestMapping("/showCalendar")
    public @ResponseBody
    ResponseMsg<> showCalendar(HttpServletRequest request)
    {

    }*/
}
