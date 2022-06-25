package com.renergy.usermanagementservice.api;

import com.renergy.usermanagementservice.feignClient.KeyCloakFeignServiceUtil;
import com.renergy.usermanagementservice.request.KeycloakUserRequest;
import com.renergy.usermanagementservice.responses.DefaultResponse;
import com.renergy.usermanagementservice.user.User;
import com.renergy.usermanagementservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyCloakFeignServiceUtil keyCloakFeignServiceUtil;

    @Transactional
    public DefaultResponse createNewUser(User user) {

        Optional<User> user1 = userRepository.findByUsername(user.getUsername());
        if(user1.isPresent()) {
            return new DefaultResponse("F001", "Request Failed : Choose a different username");
        }
        userRepository.save(user);
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization").toString();
        KeycloakUserRequest keycloakUserRequest = new KeycloakUserRequest(user.getUsername(), user.getFirstName(), user.getLastName(), user.getUsername());
        String response = keyCloakFeignServiceUtil.createUser(token,"Renergy", keycloakUserRequest);
        return new DefaultResponse("S001", "User created successfully");
    }

}
