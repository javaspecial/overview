package com.shadath.overview.model;

import java.io.Serializable;

public class MessageResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String response;
    public MessageResponse(String response) {
        this.response = response;
    }
}
