package com.example.computerstore.security;

import com.example.computerstore.security.jwt.JwtFilter;
import com.example.computerstore.security.jwt.JwtUnauthorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JwtUnauthorized unauthorized;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;
    @Value("${query.users-query}")
    private String usersQuery;
    @Value("${query.roles-query}")
    private String rolesQuery;

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/cart/**").access("hasAnyRole('CUSTOMER')")
                .antMatchers("/order/accept/**").access("hasAnyRole('MANAGER')")
                .antMatchers("/order/**").access("hasAnyRole('CUSTOMER')")
                .antMatchers("devices/allDevices").authenticated()
                .antMatchers("devices/new").access("hasAnyRole('MANAGER')")
                .antMatchers("devices/delete").access("hasAnyRole('MANAGER')")
                .antMatchers("allUsers/**").access("hasAnyRole('MANAGER')").anyRequest().permitAll()
                .and().exceptionHandling()
                .authenticationEntryPoint(unauthorized).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
