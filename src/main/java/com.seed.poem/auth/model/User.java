package com.seed.poem.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jiwei Yuan on 18-5-18.
 */

@Document(collection="users")
public class User {

    @Id
    private String id;
    private String name;
    private String password;
    private Date lastPasswordResetDate;

    private List<String> roles;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public void addRole(String role) {
        if(roles==null){
           roles=new ArrayList<String>();
        }
        roles.add(role);
    }
}
