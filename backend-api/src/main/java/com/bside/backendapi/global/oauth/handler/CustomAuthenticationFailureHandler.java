package com.bside.backendapi.global.oauth.handler;

import com.bside.backendapi.global.error.exception.BusinessException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Authentication failed3";
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        if (exception.getCause() instanceof BusinessException businessException) {
            errorMessage = businessException.getMessage();
            status = HttpStatus.valueOf(businessException.getErrorCode().getStatus());
            System.out.println("여기");
        }

        response.setStatus(status.value());
        response.getWriter().write(errorMessage);
    }
}