package com.akazimour.userapi.api;

import com.akazimour.userapi.api.dto.UserProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-ms", url = "${feign.user-ms.url}")
public interface UserApi {

    @PostMapping("/user/register")
    public UserProfileDto createNewUserProfile(@RequestBody UserProfileDto userProfileDto);

    @GetMapping("/user/all")
    public List<UserProfileDto> getAllUsers();

    @GetMapping("/user/name")
    public UserProfileDto findUserByName(@RequestParam("userName") String name);
}
