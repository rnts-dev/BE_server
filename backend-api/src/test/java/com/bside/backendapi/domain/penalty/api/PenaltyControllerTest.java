//package com.bside.backendapi.domain.penalty.api;
//
//import com.bside.backendapi.domain.penalty.application.PenaltyService;
//import com.bside.backendapi.domain.penalty.domain.vo.PenaltyType;
//import com.bside.backendapi.domain.penalty.dto.request.PenaltyCreateRequest;
//import com.bside.backendapi.domain.penalty.dto.request.TempPenaltyCreateRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.MediaType;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(PenaltyController.class)
//public class PenaltyControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PenaltyService penaltyService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @TestConfiguration
//    public static class SecurityConfig {
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .authorizeHttpRequests((requests) -> requests
//                            .anyRequest().permitAll()  // 모든 요청 허용
//                    )
//                    .csrf((csrf) -> csrf.disable());  // CSRF 보호 비활성화
//
//            return http.build();
//        }
//    }
//
//    @Test
//    @WithMockUser
//    void createPenalty() throws Exception {
//        // Given
//        TempPenaltyCreateRequest request = TempPenaltyCreateRequest.of(PenaltyType.PUNISHMENT, "Test Content");;
//        Long appointmentId = 1L;
//        Long penaltyId = 100L;
//
//        // Mocking
//        when(penaltyService.create(any(), anyLong(), anyLong())).thenReturn(penaltyId);
//
//        // When & Then
//        mockMvc.perform(post("/api/v1/penalty/{appointmentId}", appointmentId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.penaltyId").value(penaltyId))
//                .andExpect(jsonPath("$.success").value(true));
//    }
//
//}
