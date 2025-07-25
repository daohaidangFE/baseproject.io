package com.daohaidang.baseproject.repository;

import com.daohaidang.baseproject.common.enums.ERole;
import com.daohaidang.baseproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);

}
