package com.seed.poem.auth.web;

import com.seed.poem.JsonResult;
import com.seed.poem.auth.model.User;
import com.seed.poem.auth.repo.UserRepository;
import com.seed.poem.auth.service.AuthService;
import com.seed.poem.business.model.Poem;
import com.seed.poem.business.repo.PoemRepository;
import com.seed.poem.config.jwt.JwtAuthenticationRequest;
import com.seed.poem.config.jwt.JwtAuthenticationResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by seedinwind on 18/5/20.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "登录")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
//    })
    @ApiImplicitParam(name = "authenticationRequest", value = "JWT登录验证类", required = true,
            dataType = "JwtAuthenticationRequest")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public JsonResult<String> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        return authService.login(authenticationRequest.getAccount(), authenticationRequest.getPassword());
    }

    @ApiOperation(value = "刷新Token")
    @ApiImplicitParam(name = "request", value = "请求信息（带有tokenHeader）", required = true,
            dataType = "HttpServletRequest")
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @ApiOperation(value = "注册")
    @ApiImplicitParam(name = "addedUser", value = "用户实体", required = true, dataType = "User")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult<User> register(@RequestBody User addedUser) throws AuthenticationException {
        return authService.register(addedUser);
    }


}
