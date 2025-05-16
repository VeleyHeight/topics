package com.example.demo.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;



public class SecurityConfig{
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                authorizationManagerRequestMatcherRegistry
//                        .requestMatchers(HttpMethod.GET,"/topics").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/topics").authenticated()
//                        .requestMatchers(HttpMethod.PUT,"/topics/").authenticated()
//                        .requestMatchers(HttpMethod.PATCH,"/topics/").authenticated()
//                        .anyRequest().authenticated())
//                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.defaultSuccessUrl("/topics"))
//                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/topics"))
//                .oauth2Login(Customizer.withDefaults())
//                .build();
//    }
}
