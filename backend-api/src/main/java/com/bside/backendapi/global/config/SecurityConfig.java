package com.bside.backendapi.global.config;

import com.bside.backendapi.global.security.principal.CustomAuthenticationProvider;
import com.bside.backendapi.global.security.principal.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(new CustomAuthenticationProvider())
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/public/**").permitAll())
                .build();
    }
}
//
//    @RequiredArgsConstructor
//    @Configuration
//    @EnableWebSecurity
//    @EnableMethodSecurity
//    @EnableWebMvc
//    public class SecurityConfig_temp implements WebMvcConfigurer {
//
//        private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/api/**", "/kakao/login"};
//
//
//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//            http
//                    .csrf(csrf -> csrf.disable())
//                    .authorizeHttpRequests(authorizeRequests ->
//                            authorizeRequests.requestMatchers(allowedUrls).permitAll()
//                                    .anyRequest().authenticated()
//                    )
//                    .sessionManagement(sessionManagement ->
//                            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    )
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                    .cors(cors -> cors.disable());
//
//
//            return http.build();
//        }
//
//        @Override
//        public void addCorsMappings(CorsRegistry registry) {
//            registry.addMapping("/**")
//                    .allowedOriginPatterns("*") // 모든 도메인을 허용
//                    .allowedMethods("*") // 모든 HTTP 메서드를 허용
//                    .allowedHeaders("*") // 모든 헤더를 허용
//                    .allowCredentials(true);
//        }
//
//    }
