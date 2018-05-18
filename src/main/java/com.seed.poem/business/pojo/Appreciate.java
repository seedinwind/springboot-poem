package com.seed.poem.business.pojo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="appreciates")
public class Appreciate{

    @Id private String id;

    private String passageId;
    private String userId;
    private String content;
    private String like;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassageId() {
        return passageId;
    }

    public void setPassageId(String passageId) {
        this.passageId = passageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}