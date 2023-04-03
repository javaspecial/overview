package com.shadath.overview.service;

import com.shadath.overview.authentication.JWTToken;
import com.shadath.overview.domain.Role;
import com.shadath.overview.domain.User;
import com.shadath.overview.model.JWTResponse;
import com.shadath.overview.model.MessageResponse;
import com.shadath.overview.model.TokenRequest;
import com.shadath.overview.model.UserDetailsExtended;
import com.shadath.overview.model.enums.RoleType;
import com.shadath.overview.repository.RoleRepo;
import com.shadath.overview.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private JWTToken jwtToken;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public String authenticateToken(TokenRequest authenticationRequest) throws Exception {
        this.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = this.loadUserByUsername(authenticationRequest.getUsername());
        return this.jwtToken.generateToken(userDetails);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        finally {
            username = password = null;
        }
    }

    public ResponseEntity<MessageResponse> doRegister(TokenRequest tokenRequest) {
        try {
            if (this.userRepo.existsByUsername(tokenRequest.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("User name is already taken"));
            }
            User user = new User(tokenRequest.getUsername(), this.encoder.encode(tokenRequest.getPassword()));
            Set<String> stringRoles = tokenRequest.getRoles();
            Set<Role> roles = new HashSet<>();
            if (stringRoles == null) {
                roles.add(this.roleRepo.findTopByRoleTypeOrderByIdDesc(RoleType.USER).orElseThrow(() -> new RuntimeException("Role not found")));
            }
            else {
                stringRoles.forEach(role -> {
                    switch (role) {
                        case "ADMIN":
                            roles.add(this.roleRepo.findTopByRoleTypeOrderByIdDesc(RoleType.ADMIN).orElseThrow(() -> new RuntimeException("Role not found")));
                            break;
                        case "MODERATOR":
                            roles.add(this.roleRepo.findTopByRoleTypeOrderByIdDesc(RoleType.MODERATOR).orElseThrow(() -> new RuntimeException("Role not found")));
                            break;
                        default:
                            roles.add(this.roleRepo.findTopByRoleTypeOrderByIdDesc(RoleType.USER).orElseThrow(() -> new RuntimeException("Role not found")));
                    }
                });
            }
            user.setRoles(roles);
            this.userRepo.save(user);
            return ResponseEntity.ok().body(new MessageResponse("User registered successfully!"));
        }
        finally {
            tokenRequest = null;
        }
    }

    public ResponseEntity<JWTResponse> doLogin(TokenRequest tokenRequest) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtToken.generateJwtToken(authentication);
        UserDetailsExtended detailsExtended = (UserDetailsExtended) authentication.getPrincipal();
        List<String> roles = detailsExtended.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JWTResponse(jwt, detailsExtended.getId(), detailsExtended.getUsername(), detailsExtended.getPassword(), roles));
    }
}
