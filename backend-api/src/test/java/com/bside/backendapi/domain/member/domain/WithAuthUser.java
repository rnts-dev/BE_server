package com.bside.backendapi.domain.member.domain;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {
    long id() default 1L;
    String email() default "abcde@naver.com";
    String password() default "123456789";
    String name() default "김거북";
    String nickname() default "터틀킴";
}