package com.bside.backendapi.domain.cloudtest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public String createTest(String text){
        TestEntity testEntity = TestEntity.createTest(text);
        return "test";
    }
}
