package com.shadath.overview.model;

import java.io.Serializable;

public class TokenResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String JWTToken;

    public TokenResponse(String JWTToken) {
        this.JWTToken = JWTToken;
    }

    public String getToken() {
        return this.JWTToken;
    }
}
