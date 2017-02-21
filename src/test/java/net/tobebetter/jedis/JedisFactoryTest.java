package net.tobebetter.jedis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.*;

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

    /**
     * hash相关操作(适合存储多属性的对象)
     * hset hsetnx hget hgetall hkeys hvals
     * hlen hdel hexists
     * hmset hmget
     * hincrby hincrbyfloat
     * TODO: hscan
     */
    @Test
    public void testList(){
        Jedis jedis = JedisFactory.getJedis();
        //hset
        jedis.hset("user", "username", "zhang");
        jedis.hset("user", "age", "12");
        //hget
        Assert.assertEquals("zhang", jedis.hget("user", "username"));
        //hgetall 获得某对象所有键值对
        Map<String, String> getAll = jedis.hgetAll("user");
        Assert.assertEquals("12", getAll.get("age"));
        //hsetnx
        long hsetnx = jedis.hsetnx("user", "username", "wang");
        Assert.assertEquals(0, hsetnx);
        Assert.assertEquals("zhang", jedis.hget("user", "username"));
        //hkeys 获得某对象所有key
        Set<String> keys = jedis.hkeys("user");
        Assert.assertTrue(keys.contains("username"));
        //hvals 获得某对象所有key的值
        List<String> hvals = jedis.hvals("user");
        //hlen 某对象key的数量
        Assert.assertEquals(new Long(2), jedis.hlen("user"));
        //hdel 删除对象某key
        jedis.hdel("user", "age");
        Assert.assertEquals(null, jedis.hget("user", "age"));
        //hexists
        Assert.assertFalse(jedis.hexists("user", "age"));
        //hmset 同时设置多个key
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put("name", "qg");
        keyValues.put("job", "IT");
        jedis.hmset("user", keyValues);
        Assert.assertEquals("IT", jedis.hget("user", "job"));
        //hmget 同时获取多个key的值
        List<String> values = jedis.hmget("user", "name", "job");
        Assert.assertEquals(2, values.size());
        Assert.assertEquals("qg", values.get(0));
        //hincrby 增加
        jedis.hset("user", "age", "12");
        jedis.hincrBy("user", "age", 2);
        Assert.assertEquals("14", jedis.hget("user", "age"));
        //hincrbyfloat 增加一个符点数
        jedis.hincrByFloat("user", "age", 1.5);
        Assert.assertEquals("15.5", jedis.hget("user", "age"));
    }


    /**
     * 全局相关操作
     * del exists keys randomkey
     * expire pexpire expireat pexpireat  ttl pttl persist (set getset会覆写生存时间)
     * select(0-15，每次获取链接，都为数据库0)
     * move(如果目标数据库有相同名字的key，则无任何效果)
     * type
     * rename renamenx
     * TODO dump migrate object restore sort scan
     */
    @Test
    public void testGlobal() throws InterruptedException {
        Jedis jedis = JedisFactory.getJedis();
        jedis.set("username", "zhang");
        //exists  是否存在key
        Assert.assertTrue(jedis.exists("username"));
        //del 删除key
        long delCount = jedis.del("username");
        Assert.assertTrue(!jedis.exists("username"));
        //keys 查询
        jedis.set("username", "zhang");
        Set<String> keys = jedis.keys("*");
        Assert.assertTrue(keys.contains("username"));
        //randomkey 随机返回一个key
        Assert.assertEquals("username", jedis.randomKey());
        //move 移动到
        jedis.move("username", 1);
        Assert.assertEquals(null, jedis.get("username"));
        jedis.select(1);
        Assert.assertTrue(jedis.exists("username"));
        //type 类型，none(key不存在) string list set zset hash
        Assert.assertEquals("string", jedis.type("username"));
        //expire pexpire 设置时间
        jedis.pexpire("username", 1000);
        Thread.sleep(1);
        //ttl pttl 获取过期时间
        Assert.assertTrue(jedis.pttl("username") > 0 && jedis.pttl("username") < 1000);
        jedis.set("username", "zhang");  //过期时间会被覆盖
        Assert.assertEquals(new Long(-1), jedis.pttl("username"));
        //expireAt pexpireAt 以时间戳设置过期时间
        long result2 = jedis.pexpireAt("username", new Date().getTime() + 1000); //注意与linux时间差
        Thread.sleep(1);
        Assert.assertTrue(jedis.pttl("username") > 0 && jedis.pttl("username") < 1000);
        //persist 移除过期时间
        jedis.persist("username");
        Assert.assertEquals(new Long(-1), jedis.ttl("username"));
        //rename 重命名 新名称存在时，旧值会被覆盖
        jedis.rename("username", "username1");
        Assert.assertEquals("zhang", jedis.get("username1"));
        //renamenx 新值不存在时，才会重命名
        jedis.set("name", "qg");
        Long renameResult = jedis.renamenx("username1", "name");
        Assert.assertEquals(new Long(0), renameResult);
        Assert.assertEquals("qg", jedis.get("name"));

    }

    /**
     * 删除所有key
     */
    @Before
    public void cleanAllKeys(){
        Jedis jedis = JedisFactory.getJedis();
        Set<String> keys = jedis.keys("*");
        for(String key : keys){
            jedis.del(key);
        }
        jedis.select(1);
        Set<String> keys2 = jedis.keys("*");
        for(String key : keys2){
            jedis.del(key);
        }
    }

}
