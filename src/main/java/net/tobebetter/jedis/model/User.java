package net.tobebetter.jedis.model;

import java.io.Serializable;

/**
 * Created by 0513 on 2017/2/19.
 */
public class User implements Serializable {
    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
