package com.akazimour.userservice.controller;

import com.akazimour.userservice.dto.LoginDto;
import com.akazimour.userservice.service.FacebookLoginService;
import hu.webuni.tokenlib.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtLoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    FacebookLoginService facebookLoginService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/api/user/login")
    public String login(@RequestBody LoginDto loginDto) {
        UserDetails userDetails = null;
        String fbToken = loginDto.getFbToken();
        if (ObjectUtils.isEmpty(fbToken)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
                    userDetails = (UserDetails) authentication.getPrincipal();
        }else {
            userDetails = facebookLoginService.getUserDetailsForToken(fbToken);
        }
        return "\""+ jwtService.creatJwtToken(userDetails) + "\"";
    }
}
