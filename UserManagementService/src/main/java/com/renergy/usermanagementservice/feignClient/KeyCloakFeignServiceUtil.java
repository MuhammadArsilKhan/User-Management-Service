package com.renergy.usermanagementservice.feignClient;

import com.renergy.usermanagementservice.request.KeycloakUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "keycloak-feign-client", url = "${request.keycloak.url}")
public interface KeyCloakFeignServiceUtil {

    @RequestMapping(method = RequestMethod.POST, value = "/certs/{realm}/users", consumes = "application/json")
    public String createUser(@RequestHeader(value = "Authorization") String authorizationHeader , @PathVariable("realm") String realm, KeycloakUserRequest keycloakUserRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/token", consumes = "application/x-www-form-urlencoded")
    public String loginUser();
}
