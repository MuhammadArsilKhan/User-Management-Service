package com.renergy.usermanagementservice.api;

import com.renergy.usermanagementservice.responses.DefaultResponse;
import com.renergy.usermanagementservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user-management")
@CrossOrigin
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @RequestMapping(method = RequestMethod.POST, value="/create-user")
    public @ResponseBody
    DefaultResponse addNewProduct(@RequestBody User user) {
        return userManagementService.createNewUser(user);
    }

}
