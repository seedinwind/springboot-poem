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

   public enum UserType{
        OWN,WX
    }

    @Id
    private String id;
    private String name;
    private String password;
    private Date lastPasswordResetDate;
    private String assosiateId="";

    private int type;

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

    public String getAssosiateId() {
        return assosiateId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        if(roles==null){
           roles=new ArrayList<String>();
        }
        roles.add(role);
    }
}
