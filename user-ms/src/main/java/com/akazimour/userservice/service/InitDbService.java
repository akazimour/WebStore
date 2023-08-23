package com.akazimour.userservice.service;

import com.akazimour.userservice.entity.UserProfile;
import com.akazimour.userservice.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
@Service
public class InitDbService {
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

 @Transactional
    public void createUserProfile() {
        if (!userProfileRepository.existsById("admin")){
            UserProfile userProfile = new UserProfile("admin","admin@gmail.com",passwordEncoder.encode("pass"),Set.of("admin", "customer"));
            userProfile.setFacebookId(1111L);
            userProfileRepository.save(userProfile);
        }
        if (!userProfileRepository.existsById("customer")){
            UserProfile userProfile = new UserProfile("customer","customer@gmail.com",passwordEncoder.encode("pass"),Set.of("customer"));
            userProfile.setFacebookId(2222L);
            userProfileRepository.save(userProfile);
        }
        }

        public void deleteUsers(){
     userProfileRepository.deleteAll();
        }
}
