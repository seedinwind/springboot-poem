package com.seed.poem.auth.service.impl;

import com.seed.poem.JsonResult;
import com.seed.poem.auth.model.AuthUser;
import com.seed.poem.auth.model.User;
import com.seed.poem.auth.repo.UserRepository;
import com.seed.poem.auth.service.AuthService;
import com.seed.poem.config.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@SuppressWarnings("all")
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public JsonResult<User> register(User userToAdd) {

        if (userToAdd.getName() == null) {
            return new JsonResult(1,"注册帐号错误!");
        }

        User u=userRepository.findByName(userToAdd.getName());
        if(u!=null){
            return new JsonResult(1,"该用户已存在!");
        }

        final String rawPassword = userToAdd.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
        userToAdd.addRole("ROLE_USER");
        userToAdd.setType(User.UserType.OWN.ordinal());
        try {
            return new  JsonResult<>(userRepository.save(userToAdd));
        } catch (DataIntegrityViolationException e) {
            logger.debug(e.getMessage());
            return new  JsonResult<>(500,e.getRootCause().getMessage());
        }
    }

    @Override
    public JsonResult<String> login(String username, String password) {
        if(username==null) {
            return new JsonResult(1, "帐号不存在");
        }

        if(password==null) {
            return new JsonResult(1, "密码错误");
        }
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new JsonResult(jwtTokenUtil.generateToken(userDetails));
        } catch (BadCredentialsException e) {
            logger.debug(e.getMessage());
            return new JsonResult<>(1,"帐号或密码错误");
        }
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        AuthUser user = (AuthUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}