package org.example;

import lombok.Getter;

@Getter
public class UserModel {

    private String username, email, fullName, password;

    public UserModel(String username, String email, String fullName, String password) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

}
