package com.akazimour.userservice.service;

import com.akazimour.userservice.dto.UserProfileInfo;
import com.akazimour.userservice.entity.UserProfile;
import com.akazimour.userservice.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserProfileDetailService implements UserDetailsService {
    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserProfileInfo(username,userProfile.getPassword(),userProfile.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),userProfile.getEmail());
    }
}
