package com.seed.poem.auth.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.seed.poem.JsonResult;
import com.seed.poem.OkHttpUtil;
import com.seed.poem.auth.model.AuthUser;
import com.seed.poem.auth.model.User;
import com.seed.poem.auth.model.WxTokenResult;
import com.seed.poem.auth.model.WxUserInfo;
import com.seed.poem.auth.repo.UserRepository;
import com.seed.poem.auth.service.WxAuthService;
import com.seed.poem.config.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@SuppressWarnings("all")
public class WxAuthServiceImpl implements WxAuthService {

  private final String URL_ACCESSTOKEN= "https://api.weixin.qq.com/sns/oauth2/access_token";

    @Value("${wechat.appId}")
    private String appId;

    @Value("$wechat.secret")
    private String secret;

    @Autowired
    private OkHttpUtil okhttp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public JsonResult<String> login(String code) {
        //获取accessToken  openId
        //更新保存用户信息
        //返回jwtToken
        try {
            String tokenRes=okhttp.getRequest(getAccessTokenUrl(code));
            Gson gson=new Gson();
            WxTokenResult res=  gson.fromJson(tokenRes,WxTokenResult.class);
            if(res.getAccess_token()==null){
                return JsonResult.builder().error(502,"获取微信Token失败").build();
            }else{
                User userToAdd=userRepository.findByName(res.getOpenid());
                if(userToAdd==null){
                    userToAdd=new User();
                    userToAdd.addRole("ROLE_USER");
                    userToAdd.setType(User.UserType.WX.ordinal());
                    userToAdd.setName(res.getOpenid());
                }
                userToAdd.setPassword(res.getAccess_token()+"_weixin_"+res.getRefresh_token());
                userToAdd.setLastPasswordResetDate(new Date());
                userRepository.save(userToAdd);
                return JsonResult.<String>builder().data(jwtTokenUtil.generateToken(AuthUser.create(userToAdd))).build();
            }
        }catch (IOException e){
            return JsonResult.builder().error(501,"获取微信Token失败").build();
        }
    }

    private String getAccessTokenUrl(String code) {
        return new StringBuilder()
                .append(URL_ACCESSTOKEN)
                .append("?appid=")
                .append(appId)
                .append("&secret=")
                .append(secret)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code")
                .toString();
    }

    @Override
    public void refreshToken(String refresh) {

    }

    @Override
    public WxUserInfo getWxInfo() {
        return null;
    }
}
