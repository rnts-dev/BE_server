//package com.bside.backendapi.global.security.config;
//
//import com.bside.backendapi.global.security.jwt.filter.JwtVerifyFilter;
//import com.bside.backendapi.domain.user.service.OAuth2UserService;
//import com.bside.backendapi.global.security.handler.CommonLoginFailHandler;
//import com.bside.backendapi.global.security.handler.CommonLoginSuccessHandler;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//@Configuration
//@EnableMethodSecurity
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final OAuth2UserService oAuth2UserService;
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
//        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
//        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
//        corsConfiguration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration); // 모든 경로에 대해서 CORS 설정을 적용
//
//        return source;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public CommonLoginSuccessHandler commonLoginSuccessHandler() {
//        return new CommonLoginSuccessHandler();
//    }
//
//    @Bean
//    public CommonLoginFailHandler commonLoginFailHandler() {
//        return new CommonLoginFailHandler();
//    }
//
//    @Bean
//    public JwtVerifyFilter jwtVerifyFilter() {
//        return new JwtVerifyFilter();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
//                .csrf(AbstractHttpConfigurer::disable)
//                // session 사용 x
//                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
//                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                })
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                        authorizationManagerRequestMatcherRegistry
//                                .requestMatchers("/", "/sign-up")
//                                .permitAll())
////
////                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
////                        authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
//
//                .addFilterBefore(jwtVerifyFilter(), UsernamePasswordAuthenticationFilter.class)
//
////                .formLogin(httpSecurityFormLoginConfigurer -> {
////                    httpSecurityFormLoginConfigurer
////                            .loginPage("/login")
////                            .successHandler(commonLoginSuccessHandler())
////                            .failureHandler(commonLoginFailHandler());
////                })
//
//                .oauth2Login(httpSecurityOAuth2LoginConfigurer ->
//                        httpSecurityOAuth2LoginConfigurer.loginPage("/oauth2/authorization/kakao")
//                                .successHandler(commonLoginSuccessHandler())
//                                .userInfoEndpoint(userInfoEndpointConfig ->
//                                        userInfoEndpointConfig.userService(oAuth2UserService)))
//                .build();
//    }
//}
