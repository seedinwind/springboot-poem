package com.seed.poem.auth.service;

import com.seed.poem.JsonResult;
import com.seed.poem.auth.model.WxUserInfo;

public interface WxAuthService {

    JsonResult<String> login(String code);

    void refreshToken(String refresh);

    WxUserInfo getWxInfo();
}
