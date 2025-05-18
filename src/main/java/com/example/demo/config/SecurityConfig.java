package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.demo.model.UsersRoles.ADMIN;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers(HttpMethod.GET, "/user/**").authenticated()
                        .requestMatchers( "/user/**").hasAuthority(ADMIN.getAuthority())
                        .requestMatchers(HttpMethod.GET,"/topics/**").authenticated()
                        .requestMatchers("/topics/**").hasAuthority(ADMIN.getAuthority())
                        .requestMatchers(HttpMethod.GET,"/questions/**").authenticated()
                        .requestMatchers("/questions/**").hasAuthority(ADMIN.getAuthority())
                        .requestMatchers(HttpMethod.GET, "/reactions/**").authenticated()
                        .requestMatchers( "/reactions/**").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.defaultSuccessUrl("/topics"))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/topics").logoutSuccessUrl("/login"))
//                .sessionManagement(managmentConfigurer -> managmentConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
