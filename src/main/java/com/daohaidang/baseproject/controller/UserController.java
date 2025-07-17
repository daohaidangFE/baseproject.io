package com.daohaidang.baseproject.controller;

import com.daohaidang.baseproject.common.ApiResponse;
import com.daohaidang.baseproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ApiResponse getAllUsers() {
        return ApiResponse.builder().build();
    }
}
