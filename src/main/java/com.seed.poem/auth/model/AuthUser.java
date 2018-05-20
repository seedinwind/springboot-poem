package com.seed.poem.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AuthUser implements UserDetails {

    private String id;
    private String userName;
    private String password;
    private Date lastPasswordResetDate;

    private List<String> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>();
    }

    @Override
    public String getPassword() {
        return userName;
    }

    @Override
    public String getUsername() {
        return password;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public static AuthUser create(User user){
        AuthUser au=new AuthUser();
        au.id=user.getId();
        au.userName=user.getName();
        au.password=user.getPassword();
         return au;
    }
}