package net.tobebetter.jedis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by 0513 on 2017/2/6.
 * jedis命令参考地址：http://doc.redisfans.com/
 */
public class JedisFactoryTest {
    @Test
    public void testIntanceJedis(){
        Jedis jedis = JedisFactory.getJedis();
        Assert.assertNotNull(jedis);
    }

    /**
     * 字符串相关操作：设置/获取/拼接
     * 相当于redis命令中的：
     * get set append mset mget strlen
     * incr incrby decr decrby  (decr相当于incr负数)
     * getset getrange setrange
     * setnx msetnx setex psetex
     * TODO bitcount bitop setbit
     */
    @Test
    public void testString() throws InterruptedException {
        Jedis jedis = JedisFactory.getJedis();
        //添加/设置 set
        String result = jedis.set("username", "zhang");
        Assert.assertEquals("OK", result);
        //获取 get
        String username = jedis.get("username");
        Assert.assertEquals("zhang", username);
        //拼接字符串 append
        jedis.append("username", "qg");
        String fullname = jedis.get("username");
        Assert.assertEquals("zhangqg", fullname);
        //可一次设置多个值 mset
        jedis.mset("username", "zhang", "age", "23");
        Assert.assertEquals("23", jedis.get("age"));
        //可得到多个值 mget
        List<String> user = jedis.mget("username", "age");
        Assert.assertEquals(2, user.size());
        //获取字符串长度 strlen
        Assert.assertEquals(new Long(5), jedis.strlen("username"));
        //加1操作 incr 此操作在redis中，会把字符串改为数字类型
        jedis.incr("age");
        Assert.assertEquals("24", jedis.get("age"));
        //加N操作 incrby
        jedis.incrBy("age", 2);
        Assert.assertEquals("26", jedis.get("age"));
        //加符点数
        jedis.incrByFloat("age", 2.2);
        Assert.assertEquals("28.2", jedis.get("age"));
        //获取并设置 getset
        String username1 = jedis.getSet("username", "zhang2");
        Assert.assertEquals("zhang", username1);
        Assert.assertEquals("zhang2", jedis.get("username"));
        //获取子字符串 getrange
        //负数指从后往前数 (start end 都包括)(获取从0至-1)
        Assert.assertEquals("ha", jedis.getrange("username", 1, 2));
        Assert.assertEquals("ng", jedis.getrange("username", -3, -2));
        //设置子字符串 setrange  (TODO redis中不足用\x00填充，这个在java里怎么对应？)
        jedis.setrange("username", 5, "qg");
        Assert.assertEquals("zhangqg", jedis.get("username"));
        //空时设置 setnx
        jedis.setnx("username", "aaa");
        Assert.assertEquals("zhangqg", jedis.get("username"));
        jedis.del("username");
        jedis.setnx("username", "zhang");
        Assert.assertEquals("zhang", jedis.get("username"));
        //批量空时设置，msetnx (只要有一个非空，就全部不成功)
        jedis.del("weather");
        jedis.msetnx("username", "wang", "weather", "nice");
        Assert.assertEquals(null, jedis.get("weather"));
        jedis.msetnx("weather", "nice");
        Assert.assertEquals("nice", jedis.get("weather"));
        //设置超时 setex (单位：s）
        jedis.setex("ex", 1, "1s");
//        Assert.assertEquals("1s", jedis.get("ex"));
//        Thread.sleep(1000);
//        Assert.assertEquals(null, jedis.get("ex"));
        //设置超时 psetex （单位：ms）
//        jedis.psetex("pex", 1000, "1s");
//        Assert.assertEquals("1s", jedis.get("pex"));
//        Thread.sleep(1000);
//        Assert.assertEquals(null, jedis.get("pex"));
    }

    //List相关操作
    public void testList(){
        Jedis jedis = JedisFactory.getJedis();
        jedis.set("username", "");
        //删除
        jedis.del("username");
        Assert.assertEquals(null, jedis.get("username"));
    }



    //全局相关操作
    public void testGlobal(){

    }

}
