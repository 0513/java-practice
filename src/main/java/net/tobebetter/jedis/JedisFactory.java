package net.tobebetter.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * reids连接池
 * Created by 0513 on 2017/2/6.
 */
public class JedisFactory {
    private JedisFactory(){};
    private static JedisPool pool;

    static{
        /**获取配置文件，并初始化连接池*/
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        JedisPoolConfig config = new JedisPoolConfig();
        int maxActive = Integer.parseInt(bundle.getString("redis.pool.maxActive"));
        config.setMaxTotal(maxActive);
        int maxIdle = Integer.parseInt(bundle.getString("redis.pool.maxIdle"));
        config.setMaxIdle(maxIdle);
        int minIdle = Integer.parseInt(bundle.getString("redis.pool.minIdle"));
        config.setMinIdle(minIdle);
        int maxWait = Integer.parseInt(bundle.getString("redis.pool.maxWait"));
        config.setMaxWaitMillis(maxWait);
        boolean testOnBorrow = Boolean.parseBoolean(bundle.getString("redis.pool.testOnBorrow"));
        config.setTestOnBorrow(testOnBorrow);
        boolean testOnReturn = Boolean.parseBoolean(bundle.getString("redis.pool.testOnReturn"));
        config.setTestOnReturn(testOnReturn);

        String host = bundle.getString("redis.host");
        int port = Integer.parseInt(bundle.getString("redis.port"));
        int timeout = Integer.parseInt(bundle.getString("redis.timeout"));
        String password = bundle.getString("redis.password");
        pool = new JedisPool(config, host, port, timeout, null);
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }
}
