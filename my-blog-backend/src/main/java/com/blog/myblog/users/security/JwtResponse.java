package com.blog.myblog.users.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;

    private String tokenType = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }
}
