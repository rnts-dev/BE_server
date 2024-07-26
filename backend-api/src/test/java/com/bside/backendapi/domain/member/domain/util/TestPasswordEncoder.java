//package com.bside.backendapi.domain.member.domain.util;
//
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class TestPasswordEncoder implements PasswordEncoder {
//
//    // LOG_ROUNDS : BCrypt hashing 계산 복잡성을 결정 (높을수록 복잡, 10 또는 12 권장)
//    private static final int LOG_ROUNDS = 4;
//
//    @Override
//    public String encode(CharSequence rawPassword) {
//        // BCrypt.gensalt(LOG_ROUNDS)를 통해 salt 생성하고 hash 만들기
//        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(LOG_ROUNDS));
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        // 입력된 rawPassword와 encodedPassword 비교
//        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
//    }
//
//    // TestPasswordEncoder의 인스턴스를 반환하는 정적 팩토리 메서드
//    // 비밀번호 인코더를 쉽게 초기화하기 위해 작성
//    public static PasswordEncoder initialize() {
//        return new TestPasswordEncoder();
//    }
//}
