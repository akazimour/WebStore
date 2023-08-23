package com.akazimour.userservice.mapper;


import com.akazimour.userapi.api.dto.UserProfileDto;
import com.akazimour.userservice.entity.UserProfile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile userProfileDtoToUserProfile(UserProfileDto userProfileDto);
    UserProfileDto userProfileToUserProfileDto(UserProfile userProfile);
    List<UserProfile> userProfileDtosToUserProfiles (List<UserProfileDto> userProfileDtos);
   List<UserProfileDto> userProfilesToUserProfileDtos (List<UserProfile> userProfiles);
}
