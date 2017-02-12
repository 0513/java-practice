package net.tobebetter.jedis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by 0513 on 2017/2/6.
 */
public class JedisFactoryTest {
    @Test
    public void testIntanceJedis(){
        Jedis jedis = JedisFactory.getJedis();
        Assert.assertNotNull(jedis);
    }

    /**
     * 字符串相关操作：设置/获取/拼接
     */
    @Test
    public void testString(){
        Jedis jedis = JedisFactory.getJedis();
        //添加/设置
        String result = jedis.set("username", "zhang");
        Assert.assertEquals("OK", result);
        String username = jedis.get("username");
        Assert.assertEquals("zhang", username);
        //拼接字符串
        jedis.append("username", "qg");
        String fullname = jedis.get("username");
        Assert.assertEquals("zhangqg", fullname);
        //删除
        jedis.del("username");
        Assert.assertEquals(null, jedis.get("username"));
        //可一次设置多个值
        jedis.mset("username", "zhang", "age", "23");
        Assert.assertEquals("23", jedis.get("age"));
        //加1操作
        jedis.incr("age");
        Assert.assertEquals("24", jedis.get("age"));
    }

    //List相关操作
    public void testList(){

    }

}
