package com.seed.poem.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUser implements UserDetails {

    private String id;
    private String userName;
    private String password;
    private Date lastPasswordResetDate;
    private Collection<? extends GrantedAuthority> authorities;
    private List<String> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static AuthUser create(User user){
        AuthUser au=new AuthUser();
        au.id=user.getId();
        au.userName=user.getName();
        au.password=user.getPassword();
        au.authorities=mapToGrantedAuthorities(user.getRoles());
        au.lastPasswordResetDate=user.getLastPasswordResetDate();
        return au;
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}