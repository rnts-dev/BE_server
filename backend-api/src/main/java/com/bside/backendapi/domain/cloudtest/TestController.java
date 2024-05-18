package com.bside.backendapi.domain.cloudtest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @PostMapping("/")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String createTest(@RequestBody String text){
        return testService.createTest(text);
    }
}
