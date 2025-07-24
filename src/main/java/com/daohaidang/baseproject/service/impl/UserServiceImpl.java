package com.daohaidang.baseproject.service.impl;

import com.daohaidang.baseproject.common.enums.ERole;
import com.daohaidang.baseproject.dto.request.LoginRequest;
import com.daohaidang.baseproject.dto.request.RegisterRequest;
import com.daohaidang.baseproject.dto.response.JwtResponse;
import com.daohaidang.baseproject.entity.Role;
import com.daohaidang.baseproject.entity.User;
import com.daohaidang.baseproject.entity.UserRole;
import com.daohaidang.baseproject.repository.RoleRepository;
import com.daohaidang.baseproject.repository.UserRepository;
import com.daohaidang.baseproject.security.jwt.JwtUtils;
import com.daohaidang.baseproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    @Override
    @Transactional
    public void register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already taken!");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .userRoles(new HashSet<>())
                .build();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role 'USER' is not found!"));

        UserRole userRoleLink = new UserRole();
        userRoleLink.setUser(user);
        userRoleLink.setRole(userRole);

        user.getUserRoles().add(userRoleLink);

        userRepository.save(user);
    }

}
