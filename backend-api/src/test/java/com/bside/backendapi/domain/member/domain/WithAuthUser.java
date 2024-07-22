package com.bside.backendapi.domain.member.domain;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {
    long id() default 1L;
    String loginId() default "givenID";
    String email() default "givenmail@naver.com";
    String password() default "givenpw123!";
    String name() default "테스트이름";
    String nickname() default "givennick";
}