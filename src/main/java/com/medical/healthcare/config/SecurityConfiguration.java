package com.medical.healthcare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
            .authorizeRequests()
                .antMatchers("/patient/**").hasRole("USER")
                .antMatchers("/doctor/**").hasRole("DOCTOR")
                .antMatchers("/pharmacist/**").hasRole("PHARMACIST")
            .antMatchers(
                "/registration**",
                "/js/**",
                "/css/**",
                "/img/**",
                "/webjars/**",
                "/h2/**","/index/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login").successHandler(authenticationSuccessHandler())
            /* .defaultSuccessUrl("/index") */
            .permitAll()
            .and()
            .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout")
            .permitAll();


    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws  ServletException, IOException {
                // Get the authenticated user's role
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals("ROLE_USER")) {
                        // Redirect to patient template
                        response.sendRedirect("/patient");
                        return;
                    } else if (authority.getAuthority().equals("ROLE_DOCTOR")) {
                        // Redirect to doctor template
                        response.sendRedirect("/doctor");
                        return;
                    }else if (authority.getAuthority().equals("ROLE_PHARMACIST")) {
                        response.sendRedirect("/pharmacist");
                        return;
                    }
                }
                // If no role is found, redirect to index
                response.sendRedirect("/index");
            }
        };
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider());
    }

}