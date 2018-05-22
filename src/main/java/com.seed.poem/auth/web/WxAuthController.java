package com.seed.poem.auth.web;

import com.seed.poem.JsonResult;
import com.seed.poem.auth.service.AuthService;
import com.seed.poem.auth.service.WxAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class WxAuthController {

    @Autowired
    private WxAuthService authService;

    @RequestMapping(value = "/auth/login/wechat", method = RequestMethod.POST)
    public JsonResult<String> login(@RequestParam(value="code")String code) throws AuthenticationException {
        return authService.login(code);
    }
}
