package com.hungnv.tourbooking.dto;

public class GoogleTokenDTO {
    private String token;

    public GoogleTokenDTO() {}

    public GoogleTokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
