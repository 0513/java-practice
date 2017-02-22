package net.tobebetter.mongo_db;

import com.mongodb.*;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by 0513 on 2017/2/21.
 */
public class SimpleConnectTest {
    /**
     * 测试简单连接
     */
    @Test
    public void connect(){
        MongoCredential credential = MongoCredential.createCredential("root", "admin", "1".toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress("192.168.99.194", 27017), Arrays.asList(credential));
        DB db = mongoClient.getDB("test");
        DBCollection coll = db.getCollection("post");
        DBCursor cursor = coll.find();
        if(cursor.hasNext()){
            DBObject o = cursor.next();
            System.out.println(o);
        }
    }
}
