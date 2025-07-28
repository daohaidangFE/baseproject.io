package com.daohaidang.baseproject;

import com.daohaidang.baseproject.common.enums.ERole;
import com.daohaidang.baseproject.entity.Role;
import com.daohaidang.baseproject.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableCaching
public class BaseProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner initializeRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {

                System.out.println("Creating ROLE_USER...");
                roleRepository.save(new Role(ERole.ROLE_USER));
            }
            if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
                System.out.println("Creating ROLE_ADMIN...");
                roleRepository.save(new Role(ERole.ROLE_ADMIN));
            }
        };
    }
}
