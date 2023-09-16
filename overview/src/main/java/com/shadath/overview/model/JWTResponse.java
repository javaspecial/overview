package com.shadath.overview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shadath.overview.authentication.JWTResponseSerializer;

import java.util.List;

@JsonSerialize(using = JWTResponseSerializer.class)
public class JWTResponse {
    @JsonProperty("jwtToken")
    private String jwtToken;
    @JsonProperty("username")
    String username;
    String password;
    @JsonProperty("roles")
    List<String> roles;
    @JsonProperty("id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }


    public JWTResponse() {
    }

    public JWTResponse(String jwtToken, Long id, String username, String password, List<String> roles) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.id = id;
    }
}
