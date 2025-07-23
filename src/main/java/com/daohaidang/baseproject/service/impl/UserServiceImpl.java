package com.daohaidang.baseproject.service.impl;

import com.daohaidang.baseproject.common.enums.ERole;
import com.daohaidang.baseproject.dto.request.RegisterRequest;
import com.daohaidang.baseproject.entity.Role;
import com.daohaidang.baseproject.entity.User;
import com.daohaidang.baseproject.entity.UserRole;
import com.daohaidang.baseproject.repository.RoleRepository;
import com.daohaidang.baseproject.repository.UserRepository;
import com.daohaidang.baseproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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
