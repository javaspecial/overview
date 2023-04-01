package com.shadath.overview.repository;

import com.shadath.overview.domain.Role;
import com.shadath.overview.model.enums.RoleType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
