package com.akazimour.userservice.controller;
import com.akazimour.userapi.api.UserApi;
import com.akazimour.userapi.api.dto.UserProfileDto;

import com.akazimour.userservice.mapper.UserProfileMapper;
import com.akazimour.userservice.repository.UserProfileRepository;
import com.akazimour.userservice.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController implements UserApi {
    @Autowired
    UserProfileService userProfileService;
    @Autowired
    UserProfileMapper userProfileMapper;
    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public UserProfileDto createNewUserProfile(UserProfileDto userProfileDto) {
      return (userProfileService.saveUserProfile(userProfileDto));

    }
    @Override
    public List<UserProfileDto> getAllUsers() {
        return userProfileMapper.userProfilesToUserProfileDtos(userProfileService.receiveAllUsers());
    }
    @Override
    public UserProfileDto findUserByName(String userName) {
      return userProfileMapper.userProfileToUserProfileDto(userProfileRepository.findByUserName(userName).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not found")));
    }


}
