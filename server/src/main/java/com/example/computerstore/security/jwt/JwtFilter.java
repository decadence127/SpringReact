package com.example.computerstore.security.jwt;

import com.example.computerstore.exception.ExceptionHandler;
import com.example.computerstore.models.UserModel;
import com.example.computerstore.services.implementations.TokenService;
import com.example.computerstore.services.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException{
        String jwtToken = tokenService.getToken(req);

        if(jwtToken != null && tokenService.validateToken(jwtToken)){
            try{
                String userData = tokenService.getUserModel(jwtToken);
                UserModel userModel = userService.findOneByEmail(userData);
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userModel.getRole());
                ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
                list.add(simpleGrantedAuthority);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userModel.getEmail(), null, list);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e){
                throw new ExceptionHandler(401, "Unauthorized error");
            }
            filterChain.doFilter(req, res);
        }
    }
}
