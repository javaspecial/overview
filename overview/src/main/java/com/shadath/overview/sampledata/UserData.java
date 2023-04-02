package com.shadath.overview.sampledata;

import com.shadath.overview.domain.Role;
import com.shadath.overview.domain.User;
import com.shadath.overview.model.enums.RoleType;
import com.shadath.overview.repository.RoleRepo;
import com.shadath.overview.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserData {
    public final Object lock = new Object();

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @EventListener
    public void appReady(ApplicationReadyEvent applicationReadyEvent) {
        if (!userRepo.existsByUsername("Super User")) {
            Set<Role> roles = new HashSet<>();
            Role role = new Role();
            role.setRoleType(RoleType.ADMIN);
            roleRepo.save(role);
            synchronized (lock) {
                roles.add(role);
            }

            role = new Role();
            role.setRoleType(RoleType.MODERATOR);
            roleRepo.save(role);
            synchronized (lock) {
                roles.add(role);
            }

            role = new Role();
            role.setRoleType(RoleType.USER);
            roleRepo.save(role);
            synchronized (lock) {
                roles.add(role);
            }

            User user = new User("Super User", encoder.encode("1111"));
            user.setRoles(roles);
            userRepo.save(user);

            role = null;
            user = null;
            roles = null;
        }
        applicationReadyEvent = null;
    }
}
