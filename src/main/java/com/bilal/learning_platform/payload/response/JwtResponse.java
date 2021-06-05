package com.bilal.learning_platform.payload.response;

import com.bilal.learning_platform.dto.UserDto;
import com.bilal.learning_platform.model.Role;
import com.bilal.learning_platform.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JwtResponse {
    private String accessToken;
    private UserDto user;

    public JwtResponse(String accessToken, UserDto user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
