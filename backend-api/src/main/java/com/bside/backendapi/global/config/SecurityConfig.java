package com.bside.backendapi.global.config;

import com.bside.backendapi.global.jwt.application.TokenProvider;
import com.bside.backendapi.global.jwt.filter.JwtAuthenticationEntryPoint;
import com.bside.backendapi.global.jwt.filter.JwtAuthenticationProcessingFilter;
import com.bside.backendapi.global.jwt.handler.CustomAccessDeniedHandler;
import com.bside.backendapi.global.oauth.application.CustomOAuth2UserService;
import com.bside.backendapi.global.security.filter.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration,
                                                       AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManager(authenticationConfiguration, authenticationManagerBuilder));
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setPostOnly(true);
        customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능, h2-console에 접근 가능
                        .requestMatchers("/","/css/**","/images/**","/js/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/login/**", "/api/v1/public/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated())

                .exceptionHandling(handlingConfigurer -> handlingConfigurer
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증되지 않은 사용자가 리소스에 접근할 때
                        .accessDeniedHandler(customAccessDeniedHandler)) // 인증된 사용자가 접근 권한이 없는 리소스에 접근할 때

                .oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
                        // userInfoEndpoint : 로그인 성공 후 사용자 정보 가져올 때의 설정
                        // userService : 소셜 로그인 성공 시 진행될 UserService 구현체 등록
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                )

                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new JwtAuthenticationProcessingFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (Javascript 요청 허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 앤드 IP만 허용 react)
        configuration.setAllowCredentials(false); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // /login, /board, /product/
        return source;
    }
}
