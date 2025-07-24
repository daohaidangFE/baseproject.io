package com.daohaidang.baseproject.service;

import com.daohaidang.baseproject.dto.request.LoginRequest;
import com.daohaidang.baseproject.dto.request.RegisterRequest;
import com.daohaidang.baseproject.dto.response.JwtResponse;

public interface UserService {
    void register(RegisterRequest registerRequest);
    
}
