package com.akazimour.userservice.service;


import com.akazimour.userapi.api.dto.UserProfileDto;
import com.akazimour.userservice.entity.UserProfile;
import com.akazimour.userservice.mapper.UserProfileMapper;
import com.akazimour.userservice.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserProfileService {
    @Autowired
    UserProfileMapper userProfileMapper;
    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserProfileDto saveUserProfile(UserProfileDto userProfileDto){
        Optional<UserProfile> byUserName = userProfileRepository.findByUserName(userProfileDto.getUserName());
        if (byUserName.isPresent()){
            throw new IllegalArgumentException("The user already exist!");
        }else {
            UserProfile userProfile = userProfileMapper.userProfileDtoToUserProfile(userProfileDto);
            Set<String> roles = userProfile.getRoles();
            if (roles==null){
                roles = new HashSet<>();
              roles.add("customer");
              userProfile.setRoles(roles);
            }
            String password = userProfile.getPassword();
            if (password == null){
                password = passwordEncoder.encode("pass");
            }else
                password = passwordEncoder.encode(password);
            userProfile.setPassword(password);

            userProfileRepository.save(userProfile);
            return userProfileDto;
        }
        }
    public List<UserProfile> receiveAllUsers(){
      return userProfileRepository.findAll();

    }
}
