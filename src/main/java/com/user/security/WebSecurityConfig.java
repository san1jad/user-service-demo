package com.user.security;

import org.springframework.context.annotation.Configuration;

@Configuration
/*@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)*/
public class WebSecurityConfig {
/*
    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(
                        authorizeRequest -> authorizeRequest
                                .anyRequest()
                                .authenticated())
                .oauth2ResourceServer(
                        OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }*/

}
