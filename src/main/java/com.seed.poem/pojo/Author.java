package com.seed.poem.pojo;
import org.springframework.data.annotation.Id;

public class Author{

    @Id private String id;

    private String name;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}