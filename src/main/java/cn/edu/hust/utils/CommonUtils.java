package cn.edu.hust.utils;

import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class CommonUtils {
    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static String encoderByMD5(String passwd)
    {
        MessageDigest md5= null;
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            md5 = MessageDigest.getInstance("MD5");
            //加密后的字符串
            passwd=base64en.encode(md5.digest(passwd.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return passwd;
    }

    public static Long getTime(String time)
    {
        Long t=0L;
        try {
            t=format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }


    /**
     * 查找cookie,返回Cookie的值
     * @param request
     * @param key
     * @return
     */
    public static String showCookies(HttpServletRequest request, String key){
        Cookie[] cookies = request.getCookies();//根据请求数据，找到cookie数组
        for(Cookie cookie : cookies){
            if(cookie==null)
            {
                return "";
            }
            else if(key.equals(cookie.getName()))
            {
                return cookie.getValue();
            }

        }
        return "";
    }
}
