package com.renergy.usermanagementservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renergy.usermanagementservice.request.EmailMapper;
import com.renergy.usermanagementservice.request.HelpEmailMapper;
import com.renergy.usermanagementservice.request.SubscriberMapper;
import com.renergy.usermanagementservice.responses.DefaultResponse;
import com.renergy.usermanagementservice.subscribers.Subscriber;
import com.renergy.usermanagementservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/user-management")
@CrossOrigin
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.POST, value="/create-user")
    public @ResponseBody
    DefaultResponse addNewUser(@RequestBody User user) {
        return userManagementService.createNewUser(user);
    }

    @RequestMapping(method = RequestMethod.POST,value="/addSubscriber")
    public @ResponseBody
    DefaultResponse addSubscriber(@RequestBody Subscriber subscriber)
    {
        return userManagementService.addSubscriber(subscriber);
    }

    @RequestMapping(method = RequestMethod.GET,value="/subscriber-list")
    public @ResponseBody
    Page<Subscriber> getSubscriberList(@RequestParam("page") int page, @RequestParam("size") int size) {
        return userManagementService.getAllSubscribersPage(page,size);
    }

    @RequestMapping(method = RequestMethod.DELETE,value="/subscriber/{id}")
    public @ResponseBody DefaultResponse deleteSubscriber(@PathVariable("id") Long id) {
        return userManagementService.deleteSubscriber(id);
    }

    @RequestMapping(method = RequestMethod.POST,value="/sendCustomerReview")
    public @ResponseBody
    DefaultResponse sendCustomerReview(@RequestBody String message)
    {
        return emailService.sendReview(message);
    }

    @RequestMapping(method = RequestMethod.POST,value="/sendHelpEmail")
    public @ResponseBody
    DefaultResponse sendHelpEmail(@RequestBody HelpEmailMapper helpEmailMapper)
    {
        return emailService.sendHelpEmail(helpEmailMapper);
    }

    @RequestMapping(method = RequestMethod.POST,value="/sendEmailTtoSubscribers")
    public @ResponseBody
    DefaultResponse sendEmailToSubscribers(@RequestParam("emailMapper") String emailMapper,
                                           @RequestParam(value = "files", required = false) List<MultipartFile> fileList) throws IOException, MessagingException {
        ObjectMapper objectMapper=new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        EmailMapper mapper=objectMapper.readValue(emailMapper,EmailMapper.class);
        return emailService.sendMessageToSubscribers(mapper, fileList);
    }


}
