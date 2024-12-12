package com.example.daltud2.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private String avatarUrl;
    private String password;

    // Constructor mặc định
    public User() {
    }

    // Constructor đầy đủ
    public User(String name, String email, String avatarUrl, String password) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.password = password;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Phương thức kiểm tra email hợp lệ
    public boolean isValidEmail() {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "', avatarUrl='" + avatarUrl + "'}";
    }
}
