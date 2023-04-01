package com.shadath.overview.controller;


import com.shadath.overview.authentication.JWTToken;
import com.shadath.overview.model.TokenRequest;
import com.shadath.overview.model.TokenResponse;
import com.shadath.overview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JWTAuthenticationController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody TokenRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(userService.authenticateToken(authenticationRequest));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(userService.doRegister(tokenRequest));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(userService.doLogin(tokenRequest));
    }
}

