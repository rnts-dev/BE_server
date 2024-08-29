//package com.bside.backendapi.domain.member.api;
//
//import com.bside.backendapi.domain.member.application.MemberReadService;
//import com.bside.backendapi.domain.member.application.MemberSearchService;
//import com.bside.backendapi.domain.member.application.MemberService;
//import com.bside.backendapi.domain.member.dto.JoinRequest;
//import com.bside.backendapi.domain.member.dto.MemberResponse;
//import com.bside.backendapi.global.mail.MailService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static com.bside.backendapi.domain.member.util.GivenMember.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // MOCK 환경에서 테스트
//@AutoConfigureMockMvc // MockMvc를 자동으로 구성하여 테스트 클래스에서 사용할 수 있게 설정
//@AutoConfigureRestDocs // 테스트 코드 기반으로 자동으로 Rest API 문서 작성
//@ActiveProfiles("test") // application-test.yml 파일 사용
//class MemberControllerTest {
//
//    @Autowired MockMvc mockMvc;
//    @Autowired ObjectMapper objectMapper;
//    @MockBean MemberService memberService;
//    @MockBean MemberReadService memberReadService;
//    @MockBean MemberSearchService memberSearchService;
//    @MockBean MailService mailService;
//
//    @Test
//    @DisplayName("요청을 받아 정상적으로 회원가입이 이루어진다.")
//    void join() throws Exception {
//        JoinRequest joinRequest = JoinRequest.builder()
//                .loginId(GIVEN_LOGINID)
//                .mail(GIVEN_EMAIL)
//                .password(GIVEN_PASSWORD)
//                .nickname(GIVEN_NICKNAME)
//                .name(GIVEN_NAME)
//                .birth(GIVEN_BIRTH)
//                .build();
//
//        String body = objectMapper.writeValueAsString(joinRequest);
//
//        MemberResponse response = MemberResponse.of(joinRequest.toEntity());
//
//        when(memberService.join(any())).thenReturn(response);
//
//        getCreate(body)
//                .andExpect(status().isCreated())
//                .andDo(document("member/join",
//                        requestFields(
//                                fieldWithPath("loginId").description("아이디"),
//                                fieldWithPath("mail").description("이메일"),
//                                fieldWithPath("password").description("패스워드"),
//                                fieldWithPath("name").description("이름"),
//                                fieldWithPath("nickname").description("닉네임"),
//                                fieldWithPath("birth").description("생년월일")
//                        ),
//                        responseFields(
//                                fieldWithPath("id").description("회원 고유 식별자"),
//                                fieldWithPath("loginId").description("아이디"),
//                                fieldWithPath("name").description("이름")
//                        )))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("하나라도 null이면 error 발생")
//    void join_null() throws Exception {
//        JoinRequest joinRequest = JoinRequest.builder()
//                .loginId(GIVEN_LOGINID)
//                .mail(GIVEN_EMAIL)
//                .password(GIVEN_PASSWORD)
//                .nickname(GIVEN_NICKNAME)
//                .name(null)
//                .birth(GIVEN_BIRTH)
//                .build();
//
//        String body = objectMapper.writeValueAsString(joinRequest);
//
//        MemberResponse response = MemberResponse.of(joinRequest.toEntity());
//
//        when(memberService.join(any())).thenReturn(response);
//
//        getCreate(body).andExpect(status().isBadRequest())
//                .andDo(print());
//    }
//
//    @Test
//    void getDetailById() throws Exception {
//
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void updateTendency() {
//    }
//
//    @Test
//    void searchId() {
//    }
//
//    @Test
//    void updatePassword() {
//    }
//
//    @Test
//    void getPrincipal() {
//    }
//
//    private ResultActions getCreate(String body) throws Exception {
//        return mockMvc.perform(post("/api/v1/public/members").content(body)
//                .contentType(MediaType.APPLICATION_JSON));
//    }
//}