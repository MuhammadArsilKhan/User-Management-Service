package com.renergy.usermanagementservice.api.session;

import com.renergy.usermanagementservice.request.LoginRequestMapper;
import com.renergy.usermanagementservice.responses.DefaultResponse;
import com.renergy.usermanagementservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/session")
@CrossOrigin
public class SessionController {

    @Autowired
    SessionService sessionService;

    @RequestMapping(method = RequestMethod.POST, value="/login")
    public @ResponseBody
    ResponseEntity<?> loginUser(@RequestBody LoginRequestMapper loginRequestMapper) throws URISyntaxException {
        return sessionService.userLogin(loginRequestMapper);
    }
}
