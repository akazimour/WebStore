package com.akazimour.userservice.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserProfileInfo extends User {

    private String email;


    public UserProfileInfo(String username, String password, Collection<? extends GrantedAuthority> authorities,String email) {
        super(username, password, authorities);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
