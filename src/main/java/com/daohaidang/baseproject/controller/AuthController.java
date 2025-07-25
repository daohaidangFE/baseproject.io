package com.daohaidang.baseproject.controller;

import com.daohaidang.baseproject.common.ApiResponse;
import com.daohaidang.baseproject.dto.request.LoginRequest;
import com.daohaidang.baseproject.dto.request.RegisterRequest;
import com.daohaidang.baseproject.dto.response.JwtResponse;
import com.daohaidang.baseproject.entity.User;
import com.daohaidang.baseproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
        ApiResponse<String> response = ApiResponse.success("User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userService.login(loginRequest);
        ApiResponse<JwtResponse> response = ApiResponse.success(jwtResponse, "Login success");
        return ResponseEntity.ok(response);
    }

}
