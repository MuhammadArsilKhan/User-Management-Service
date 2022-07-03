package com.renergy.usermanagementservice.api;

import com.renergy.usermanagementservice.feignClient.KeyCloakFeignServiceUtil;
import com.renergy.usermanagementservice.request.KeycloakUserRequest;
import com.renergy.usermanagementservice.request.SubscriberMapper;
import com.renergy.usermanagementservice.responses.DefaultResponse;
import com.renergy.usermanagementservice.subscribers.Subscriber;
import com.renergy.usermanagementservice.subscribers.SubscriberRepository;
import com.renergy.usermanagementservice.user.User;
import com.renergy.usermanagementservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

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

    public DefaultResponse addSubscriber(Subscriber subscriber)
    {
        subscriber.setCreatedDate(new Date());

        subscriberRepository.save(subscriber);

        System.out.println("Subscriber Added");

        return new DefaultResponse("200","Subscriber added successfully",subscriber.getId().toString());
    }

    public Page<Subscriber> getAllSubscribersPage(int page, int offset) {

        Pageable paging = PageRequest.of(page, offset);
        return subscriberRepository.findAll(paging);
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public DefaultResponse deleteSubscriber(Long id) {
        System.out.println("Deleting Subscriber with id :" + id);
        subscriberRepository.deleteById(id);
        return new DefaultResponse("200","Subscriber deleted successfully",id.toString());
    }

}
