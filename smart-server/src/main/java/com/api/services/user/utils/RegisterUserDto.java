package com.api.services.user.utils;

public class RegisterUserDto extends AbstractUserDto{
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
