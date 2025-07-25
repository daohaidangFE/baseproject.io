package com.daohaidang.baseproject.controller;

import com.daohaidang.baseproject.common.ApiResponse;
import com.daohaidang.baseproject.dto.response.UserProfileResponse;
import com.daohaidang.baseproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserProfileResponse>>> getAllUsers() {
        List<UserProfileResponse> users = userService.getAllUsers();
        ApiResponse<List<UserProfileResponse>> apiResponse = ApiResponse.success(users, "Success");
        return ResponseEntity.ok(apiResponse);
    }
}
