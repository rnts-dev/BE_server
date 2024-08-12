package com.bside.backendapi.global.config;

import com.bside.backendapi.global.jwt.application.TokenProvider;
import com.bside.backendapi.global.jwt.filter.JwtAuthenticationEntryPoint;
import com.bside.backendapi.global.jwt.filter.JwtAuthenticationProcessingFilter;
import com.bside.backendapi.global.jwt.handler.CustomAccessDeniedHandler;
import com.bside.backendapi.global.oauth.application.CustomOAuth2UserService;
import com.bside.backendapi.global.oauth.handler.CustomAuthenticationFailureHandler;
import com.bside.backendapi.global.oauth.handler.CustomAuthenticationSuccessHandler;
import com.bside.backendapi.global.security.filter.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthenticationConfiguration authenticationConfiguration;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final TokenProvider tokenProvider;

    private static final String PUBLIC = "/api/v1/public/**";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration,
                                                       AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // ../auth/login 요청시 실행 (컨트롤러, 서비스 필요 X)
        // 헤더에 "Authorization" : "access token" 전달
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManager(authenticationConfiguration, authenticationManagerBuilder));
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setPostOnly(true); // 항상 POST 처리
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능, h2-console에 접근 가능
                        .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**").permitAll()
                        .requestMatchers(PUBLIC, "/swagger-ui/**", "/v3/api-docs/**", "/login").permitAll()
                        .requestMatchers("/error").permitAll() // '/error' 경로에 대한 접근 허용
                        .anyRequest().authenticated())

                .exceptionHandling(handlingConfigurer -> handlingConfigurer
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증되지 않은 사용자가 리소스에 접근할 때
                        .accessDeniedHandler(customAccessDeniedHandler)) // 인증된 사용자가 접근 권한이 없는 리소스에 접근할 때

                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilter(customAuthenticationFilter)

                .addFilterBefore(new JwtAuthenticationProcessingFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                // OAuth 로그인
                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
//                        .loginPage("/login") // 권한 접근 실패 시 이동할 페이지
//                        .defaultSuccessUrl("/home") // 로그인 성공 시 이동할 페이지
//                        .failureUrl("/login?error") // 로그인 실패 시 이동할 페이지
                        // userInfoEndpoint : 로그인 성공 후 사용자 정보 가져올 때의 설정
                        // userService : 소셜 로그인 성공 시 진행될 UserService 구현체 등록
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                                .successHandler(customAuthenticationSuccessHandler)
                                .failureHandler(customAuthenticationFailureHandler())
                )
                .build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        // localhost:8080 백엔드, localhost:3000 프론트엔드
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(false); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // /login, /board, /product/
        return source;
    }
}
