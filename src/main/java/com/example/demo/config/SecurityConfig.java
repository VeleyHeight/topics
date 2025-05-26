package com.example.demo.config;

//import com.example.demo.JWT.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import static com.example.demo.model.UsersRoles.ADMIN;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
//    private final JWTFilter jwtFilter;
    private final String name = "topics.";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
//                .sessionManagement(managementConfigurer -> managementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers("/questions/**").hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers("/reactions/**").hasAuthority("ADMIN")
                                .requestMatchers("/topics/**").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/**").authenticated()
//                                .requestMatchers("/user/**").hasAuthority(name+(ADMIN.getAuthority()).toLowerCase())
//                                .requestMatchers("/topics/**").hasAuthority(ADMIN.getAuthority())
//                                .requestMatchers("/questions/**").hasAuthority(ADMIN.getAuthority())
//                                .requestMatchers("/reactions/**").hasAuthority(ADMIN.getAuthority())
//                                .requestMatchers("/topics").fullyAuthenticated()
                                .anyRequest().authenticated())
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(converter())))
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }

    private JwtAuthenticationConverter converter(){
        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtAuthentificationConverter());
        return converter;
    }


}
