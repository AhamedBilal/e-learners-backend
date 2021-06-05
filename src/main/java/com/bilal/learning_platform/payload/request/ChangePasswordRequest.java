package com.bilal.learning_platform.payload.request;

public class ChangePasswordRequest {
    private String username;
    private String email;
    private String oldPassword;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
