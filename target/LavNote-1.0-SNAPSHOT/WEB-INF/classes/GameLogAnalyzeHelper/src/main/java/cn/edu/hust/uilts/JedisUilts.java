package cn.edu.hust.uilts;

import cn.edu.hust.constant.Constant;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUilts {
    private static Jedis jedis =null;
    private static JedisPool pool=null;
    private static GenericObjectPoolConfig conf=null;

    static
    {
        conf=new GenericObjectPoolConfig();
        pool=new JedisPool(conf, Constant.REDIS_IP,Constant.REDIS_PORT,Constant.TIMEOUT,Constant.PASSWD);
    }

    public static Jedis getJedis()
    {
        jedis=pool.getResource();
        return jedis;
    }

    public static void close()
    {
        pool.returnResource(jedis);
    }
}
