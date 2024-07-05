package com.bside.backendapi.global.config;

import com.bside.backendapi.global.jwt.JwtAccessDeniedHandler;
import com.bside.backendapi.global.jwt.JwtAuthenticationEntryPoint;
import com.bside.backendapi.global.jwt.JwtFilter;
import com.bside.backendapi.global.jwt.TokenProvider;
import com.bside.backendapi.global.oauth.application.CustomOAuth2UserService;
import com.bside.backendapi.global.oauth.handler.CustomSuccessHandler;
import com.bside.backendapi.global.security.principal.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomOAuth2UserService customOAuth2UserService;

    private static final String PUBLIC = "/api/v1/public/**";

    // 토큰 생성시 인증 부여할 AuthenticationProvider 구현체
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

                .cors(cors -> cors.configurationSource(configurationSource()))

                .httpBasic(AbstractHttpConfigurer::disable)

                .exceptionHandling(handlingConfigurer -> handlingConfigurer
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))

                .authenticationProvider(customAuthenticationProvider())

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PUBLIC, "/api/v1/**","/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())

                .sessionManagement(sessionManagement
                        -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)

                .formLogin(AbstractHttpConfigurer::disable)

                // OAuth 로그인
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
//                        .loginPage("") // 권한 접근 실패 시 이동할 페이지
//                        .defaultSuccessUrl("") // 로그인 성공 시 이동할 페이지
//                        .failureUrl("") // 로그인 실패 시 이동할 페이지

                        // userInfoEndpoint : 로그인 성공 후 사용자 정보 가져올 때의 설정
                        // userService : 소셜 로그인 성공 시 진행될 UserService 구현체 등록
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                                .successHandler(new CustomSuccessHandler(tokenProvider))
                )

                .build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        // localhost:8080 백엔드, localhost:3000 프론트엔드
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization"); // 옛날에는 디폴트 였다. 지금은 아닙니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // /login, /board, /product/
        return source;
    }
}
