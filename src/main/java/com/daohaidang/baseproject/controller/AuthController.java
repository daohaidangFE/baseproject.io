package com.daohaidang.baseproject.controller;

import com.daohaidang.baseproject.common.ApiResponse;
import com.daohaidang.baseproject.entity.User;
import com.daohaidang.baseproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody User user) {
        return ApiResponse.builder().build();
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody User user) {
        return ApiResponse.builder().build();
    }

}
