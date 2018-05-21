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
            return JsonResult.<User>builder().error(1,"注册帐号错误!").build();
        }

        final String rawPassword = userToAdd.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetDate(new Date());
//            Role userRole = roleRepository.findByName("ROLE_USER");
//            if (userRole == null){
//            userRole = roleRepository.save(new Role("ROLE_USER"));
//        }
//        userToAdd.setRoles(Collections.singletonList(userRole));
        userToAdd.addRole("ROLE_USER");
        try {
            return JsonResult.<User>builder().data(userRepository.save(userToAdd)).build();
        } catch (DataIntegrityViolationException e) {
            logger.debug(e.getMessage());
            return JsonResult.<User>builder().error(500,e.getRootCause().getMessage()).build();
        }
    }

    @Override
    public JsonResult<String> login(String username, String password) {
        if(username==null) {
            return JsonResult.<String>builder().error(1, "帐号不存在").build();
        }

        if(password==null) {
            return JsonResult.<String>builder().error(1, "密码错误").build();
        }
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return JsonResult.<String>builder().data(jwtTokenUtil.generateToken(userDetails)).build();
        } catch (BadCredentialsException e) {
            logger.debug(e.getMessage());
            return JsonResult.<String>builder().error(1,"帐号或密码错误").build();
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