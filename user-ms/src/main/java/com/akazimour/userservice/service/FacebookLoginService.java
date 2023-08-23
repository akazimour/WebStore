package com.akazimour.userservice.service;


import com.akazimour.userservice.entity.UserProfile;
import com.akazimour.userservice.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;
import java.util.Set;

@Service
public class FacebookLoginService {

    private static final String GRAPH_API_BASE_URL ="https://graph.facebook.com/13.0";
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    UserProfileDetailService userProfileDetailService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public static class FacebookData{
        private String email;
        private Long fbId;

        public FacebookData() {
        }

        public FacebookData(String email, Long fbId) {
            this.email = email;
            this.fbId = fbId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Long getFbId() {
            return fbId;
        }

        public void setFbId(Long fbId) {
            this.fbId = fbId;
        }
    }
@Transactional
    public UserDetails getUserDetailsForToken(String fbToken) {
        FacebookData fbData = getEmailOfFbUser(fbToken);
        UserProfile orCreateUser = findOrCreateUser(fbData);
        userProfileDetailService.loadUserByUsername(orCreateUser.getUserName());
        return null;
    }

    private FacebookData getEmailOfFbUser(String token){
        return WebClient
                .create(GRAPH_API_BASE_URL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/me")
                        .queryParam("fields","email,name")
                        .build())
                .headers(httpHeaders ->httpHeaders.setBearerAuth(token))
                .retrieve()
                .bodyToMono(FacebookData.class)
                .block();
    }
    private UserProfile findOrCreateUser(FacebookData facebookData){
        Optional<UserProfile> byFacebookId = userProfileRepository.findByFacebookId(facebookData.getFbId());
        if (byFacebookId.isEmpty()){
            UserProfile sUser = new UserProfile(facebookData.email,facebookData.email,passwordEncoder.encode("dummy"),Set.of("customer"));
            sUser.setFacebookId(facebookData.fbId);
            return userProfileRepository.save(sUser);
        }else
            return byFacebookId.get();
    }




}
