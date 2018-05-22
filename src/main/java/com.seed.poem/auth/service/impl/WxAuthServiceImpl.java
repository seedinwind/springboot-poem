package com.seed.poem.auth.service.impl;

import com.seed.poem.JsonResult;
import com.seed.poem.OkHttpUtil;
import com.seed.poem.auth.model.WxUserInfo;
import com.seed.poem.auth.service.WxAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class WxAuthServiceImpl implements WxAuthService {

    @Autowired
    private OkHttpUtil okhttp;

    @Override
    public JsonResult<String> login(String code) {
        //获取accessToken  openId
        //更新保存用户信息
        //返回jwtToken

        return null;
    }

    @Override
    public void refreshToken(String refresh) {

    }

    @Override
    public WxUserInfo getWxInfo() {
        return null;
    }
}
