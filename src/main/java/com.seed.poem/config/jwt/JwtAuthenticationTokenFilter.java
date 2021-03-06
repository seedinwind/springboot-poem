package com.seed.poem.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

//    @Value("${jwt.tokenHead}")
//    private String tokenHead;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null) {
            String account = jwtTokenUtil.getUsernameFromToken(authHeader);

            logger.info("checking authentication " + account);

            if (account != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                //TODO jwt token验证  直接使用token数据
                // 如果我们足够相信token中的数据，也就是我们足够相信签名token的secret的机制足够好
                // 这种情况下，我们可以不用再查询数据库，而直接采用token中的数据
                // 本例中，我们还是通过Spring Security的 @UserDetailsService 进行了数据查询
                // 但简单验证的话，你可以采用直接验证token是否合法来避免昂贵的数据查询
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(account);

                if (jwtTokenUtil.validateToken(authHeader)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account
                            , null, Collections.emptyList());
//                    authentication.setDetails(new WebAuenticatthionDetailsSource().buildDetails(
//                            request));
                    logger.info("authenticated user " + account + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);

    }
}
