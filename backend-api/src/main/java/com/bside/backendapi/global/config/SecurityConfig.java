package com.bside.backendapi.global.config;

import com.bside.backendapi.global.jwt.filter.JwtFilter;
import com.bside.backendapi.global.jwt.util.JwtUtil;
import com.bside.backendapi.global.oauth.handler.CustomSuccessHandler;
import com.bside.backendapi.global.oauth.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // csrf disable
                .csrf(AbstractHttpConfigurer::disable)

                // session -> STATELESS
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // cors 설정
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }))

                // default login form disable
                .formLogin(AbstractHttpConfigurer::disable)

                // HTTP Basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)

                //JwtFilter 추가
//                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class)

                // OAuth2 설정
                .oauth2Login((oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)))
                        .successHandler(customSuccessHandler)
                ))

                // 경로 인가 작업
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())

                .build();
    }
}