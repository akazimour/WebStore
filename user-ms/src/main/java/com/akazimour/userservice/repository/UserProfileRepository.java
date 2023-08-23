package com.akazimour.userservice.repository;


import com.akazimour.userservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,String> {
    public Optional<UserProfile> findByUserName (@Param("userName")String userName);

    public Optional<UserProfile> findByFacebookId (Long userId);
}
