package com.shadath.overview.model;

import java.io.Serializable;
import java.util.List;

public class JWTResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private String jwtResponse;
    List<String> roles;
    private Long id;
    String username;
    String password;

    public JWTResponse(String jwtResponse, Long id, String username, String password, List<String> roles) {
        this.jwtResponse = jwtResponse;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.id = id;
    }
}
