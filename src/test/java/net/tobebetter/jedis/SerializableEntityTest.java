package net.tobebetter.jedis;

import net.tobebetter.jedis.model.User;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by 0513 on 2017/2/19.
 */
public class SerializableEntityTest {
    @Test
    public void test(){
        Jedis jedis = JedisFactory.getJedis();
        User user = new User("1", "zhang");
        jedis.set("user1".getBytes(), SerializableUtil.serialize(user));
        byte[] userByte = jedis.get("user1".getBytes());
        User user2 = (User) SerializableUtil.unserialize(userByte);
        Assert.assertEquals("zhang", user2.getName());
    }
}
