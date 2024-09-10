package com.bside.backendapi.domain.member.domain;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {
    long id() default 1L;
    String loginId() default "testId";
    String email() default "hwi1386@naver.com";
    String password() default "testpw11!!";
    String nickname() default "테스트닉네임";
}