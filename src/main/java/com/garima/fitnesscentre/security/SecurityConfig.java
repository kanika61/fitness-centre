package com.garima.fitnesscentre.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class SecurityConfig 
{  
     @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public DaoAuthenticationProvider authProvider() {
    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //     authProvider.setUserDetailsService(userDetailsService);
    //     authProvider.setPasswordEncoder(encoder());
    //     return authProvider;
    // }
     
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(encoder());
    }


     @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                requests -> requests
                        .requestMatchers("/cart/view").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .requestMatchers("/**").permitAll()
            )
            .formLogin((formLogin) ->
                formLogin
                .loginPage("/login")
                )
            .logout((logout) ->
                logout
                    .logoutSuccessUrl("/")
                            )
            .exceptionHandling((exceptionHandling) ->
                    exceptionHandling
                        .accessDeniedPage("/")
                    );
            
        return http.build();
    }
   
    
}
