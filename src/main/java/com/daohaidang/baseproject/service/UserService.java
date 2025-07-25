package com.daohaidang.baseproject.service;

import com.daohaidang.baseproject.dto.request.LoginRequest;
import com.daohaidang.baseproject.dto.request.RegisterRequest;
import com.daohaidang.baseproject.dto.response.JwtResponse;
import com.daohaidang.baseproject.dto.response.UserProfileResponse;

import java.util.List;

public interface UserService {
    void register(RegisterRequest registerRequest);

    JwtResponse login(LoginRequest loginRequest);

    List<UserProfileResponse> getAllUsers();
}
