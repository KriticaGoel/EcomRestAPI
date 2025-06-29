package com.kritica.repository;

import com.kritica.model.AppRole;
import com.kritica.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByRoleName(AppRole appRole);
  //Optional findByRoleName(AppRole appRole);
}