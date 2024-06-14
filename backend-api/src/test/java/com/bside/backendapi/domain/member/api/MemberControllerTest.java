package com.bside.backendapi.domain.member.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // MOCK 환경에서 테스트
@AutoConfigureMockMvc // MockMvc를 자동으로 구성하여 테스트 클래스에서 사용할 수 있게 설정
@AutoConfigureRestDocs // 테스트 코드 기반으로 자동으로 Rest API 문서 작성
@ActiveProfiles("test") // application-test.yml 파일 사용
class MemberControllerTest {

    @Test
    void join() {
    }
}