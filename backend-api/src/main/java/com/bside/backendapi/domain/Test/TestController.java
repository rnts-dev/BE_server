package com.bside.backendapi.domain.Test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    private final  TestService testService;

    @PostMapping("/")
    public String createTest(@RequestBody String text){
        return testService.createTest(text);
    }
}
