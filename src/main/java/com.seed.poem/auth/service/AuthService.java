package com.seed.poem.auth.service;


import com.seed.poem.JsonResult;
import com.seed.poem.auth.model.User;

/**
 * Created by Yuicon on 2017/5/20.
 * https://segmentfault.com/u/yuicon
 */
public interface AuthService {

    JsonResult<User> register(User userToAdd);

    JsonResult<String> login(String username, String password);

    String refresh(String oldToken);
}
