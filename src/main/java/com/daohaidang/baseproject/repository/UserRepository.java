package com.daohaidang.baseproject.repository;

import com.daohaidang.baseproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Override
    @Query("SELECT u FROM User u JOIN FETCH u.userRoles ur JOIN FETCH ur.role")
    List<User> findAll();
}
