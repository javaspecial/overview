package com.shadath.overview.model;

import java.io.Serializable;
import java.util.Set;

public class TokenRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;
    private Set<String> roles;
    private String username;
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }


    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String>  roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

