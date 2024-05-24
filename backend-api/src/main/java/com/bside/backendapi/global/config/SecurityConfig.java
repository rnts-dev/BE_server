package com.bside.backendapi.global.config;

import com.bside.backendapi.global.jwt.filter.JwtFilter;
import com.bside.backendapi.global.jwt.util.JwtUtil;
import com.bside.backendapi.global.oauth.handler.CustomSuccessHandler;
import com.bside.backendapi.global.oauth.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;

    private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/api/**"};

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .cors(cors -> cors.disable())
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .addFilterAfter(new JwtFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class)
//                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
//                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
//                                .userService(customOAuth2UserService))
//                        .successHandler(customSuccessHandler))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(allowedUrls).permitAll()
//                        .anyRequest().authenticated())
//                .build();
//    }

//    private final CustomOAuth2UserService customOAuth2UserService;
//    private final CustomSuccessHandler customSuccessHandler;
//    private final JwtUtil jwtUtil;
//
//    private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/api/**"};
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                // csrf disable
//                .csrf(AbstractHttpConfigurer::disable)
//
//                // session -> STATELESS
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                // cors 설정
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//
//                        CorsConfiguration configuration = new CorsConfiguration();
//
//                        configuration.setAllowedOrigins(Collections.singletonList("*"));
//                        configuration.setAllowedMethods(Collections.singletonList("*"));
//                        configuration.setAllowCredentials(false);
//                        configuration.setAllowedHeaders(Collections.singletonList("*"));
//                        configuration.setMaxAge(3600L);
//
//                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
//                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
//
//                        return configuration;
//                    }
//                }))
//
//                // default login form disable
//                .formLogin(AbstractHttpConfigurer::disable)
//
//                // HTTP Basic 인증 방식 disable
//                .httpBasic(AbstractHttpConfigurer::disable)
//
//                //JwtFilter 추가
////                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(new JwtFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class)
//
//                // OAuth2 설정
//                .oauth2Login((oAuth2LoginConfigurer -> oAuth2LoginConfigurer
//                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
//                                .userService(customOAuth2UserService)))
//                        .successHandler(customSuccessHandler)
//                ))
//
//                // 경로 인가 작업
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers(allowedUrls).permitAll()
//                        .anyRequest().permitAll())
//
//                .build();
//    }

    @RequiredArgsConstructor
    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    @EnableWebMvc
    public class SecurityConfig_temp implements WebMvcConfigurer {

        private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/api/**"};


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(authorizeRequests ->
                            authorizeRequests.requestMatchers(allowedUrls).permitAll()
                                    .anyRequest().authenticated()
                    )
                    .sessionManagement(sessionManagement ->
                            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .cors(cors -> cors.disable());


            return http.build();
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOriginPatterns("*") // 모든 도메인을 허용
                    .allowedMethods("*") // 모든 HTTP 메서드를 허용
                    .allowedHeaders("*") // 모든 헤더를 허용
                    .allowCredentials(true);
        }

    }
}