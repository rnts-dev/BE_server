package com.bside.backendapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    @ResponseBody
    public String mainAPI() {
        return "main route";
    }

    @RequestMapping("/kakao-redirect")
    public String redirectUrl(@RequestParam String code) throws Exception {
        System.out.println("code = " + code);
        return "redirect:/";
    }
}
