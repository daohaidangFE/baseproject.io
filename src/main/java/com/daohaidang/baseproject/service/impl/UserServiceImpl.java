package com.daohaidang.baseproject.service.impl;

import com.daohaidang.baseproject.common.enums.ERole;
import com.daohaidang.baseproject.dto.request.LoginRequest;
import com.daohaidang.baseproject.dto.request.RegisterRequest;
import com.daohaidang.baseproject.dto.response.JwtResponse;
import com.daohaidang.baseproject.dto.response.UserProfileResponse;
import com.daohaidang.baseproject.entity.Role;
import com.daohaidang.baseproject.entity.User;
import com.daohaidang.baseproject.entity.UserRole;
import com.daohaidang.baseproject.repository.RoleRepository;
import com.daohaidang.baseproject.repository.UserRepository;
import com.daohaidang.baseproject.security.jwt.JwtUtils;
import com.daohaidang.baseproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional
//    @CacheEvict(value = "users_all", allEntries = true)
    public void register(RegisterRequest registerRequest) {
        System.out.println("======> REGISTERING NEW USER AND EVICTING 'users_all' CACHE <======");
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already taken!");
        }

        String encodePassword = passwordEncoder.encode(registerRequest.getPassword());

        logger.info("Registering user '{}'. Raw pass: '{}', Encoded pass: '{}'",
                registerRequest.getUsername(), registerRequest.getPassword(), encodePassword);

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .fullName(registerRequest.getFullName())
                .password(encodePassword)
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

    @Override
    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {

        logger.info("Attempting to login user: '{}'", loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        //Lấy thông tin chi tiết của user đã xác thực
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
//    @Cacheable(value = "users_all")
    public List<UserProfileResponse> getAllUsers() {
        System.out.println("======> EXECUTING DB QUERY TO GET ALL USERS <======");
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> UserProfileResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .fullName(user.getFullName())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
