package cn.edu.hust;

import cn.edu.hust.domain.NoteBooks;
import cn.edu.hust.utils.Constant;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class RedisTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/applicationContext*.xml"});

        BeanFactory factory = (BeanFactory) context;

        RedisTemplate redisTemplate = (RedisTemplate) factory.getBean("redisTemplate");

        List<Object> objects = redisTemplate.opsForList().range("725289914d07498d8e94e79e33218655" + ":" + Constant.NOTEBOOKS_SUFFIX, 0, 1);

        for(int i=0;i<objects.size();i++)
        {
            Object o=objects.get(i);
            NoteBooks noteBooks=(NoteBooks)(o);
        }
    }
}