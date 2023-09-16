package com.shadath.overview.authentication;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.shadath.overview.model.JWTResponse;

import java.io.IOException;

public class JWTResponseSerializer extends JsonSerializer<JWTResponse> {

    @Override
    public void serialize(JWTResponse jwtResponse, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("id", String.valueOf(jwtResponse.getId()));
        gen.writeStringField("jwtToken", jwtResponse.getJwtToken());
        gen.writeStringField("username", jwtResponse.getUsername());
        gen.writeStringField("roles", String.valueOf(jwtResponse.getRoles()));

        gen.writeEndObject();
    }
}