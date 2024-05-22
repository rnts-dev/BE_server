//package com.bside.backendapi.global.config.security;
//
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@EnableWebMvc
//public class SecurityConfig_temp implements WebMvcConfigurer {
//
//    private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/api/**"};
//
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests.requestMatchers(allowedUrls).permitAll()
//                                .anyRequest().authenticated()
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .cors(cors -> cors.disable());
//
//
//        return http.build();
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*") // 모든 도메인을 허용
//                .allowedMethods("*") // 모든 HTTP 메서드를 허용
//                .allowedHeaders("*") // 모든 헤더를 허용
//                .allowCredentials(true);
//    }


//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3002", "http://localhost:5002", "http://localhost:8080","https://localhost:8443","https://rnts405-api.p-e.kr:8443", "https://rnts405-api.p-e.kr:8080"));
//        configuration.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST", "PUT", "DELETE"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("http://localhost:3002", "http://localhost:5002", "http://localhost:8080", "https://localhost:8443" , "https://rnts405-api.p-e.kr:8443","https://rnts405-api.p-e.kr:8080")
//                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
//}