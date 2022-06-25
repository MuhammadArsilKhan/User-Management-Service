package com.renergy.usermanagementservice.api.session;

import com.renergy.usermanagementservice.request.LoginRequestMapper;
import com.renergy.usermanagementservice.responses.KeycloakLoginResponse;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class SessionService {

    @Value("${grant_type}")
    private String grantType;

    @Value("${client_id}")
    private String clientId;

    @Value("${client_secret}")
    private String clientSecret;

    @Value("${login_url}")
    private String loginURL;


    public ResponseEntity<?> userLogin(LoginRequestMapper loginRequestMapper) throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(loginURL);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = generateLoginRequestParams(loginRequestMapper);
        HttpEntity<?> request = new HttpEntity<>(params, headers);
        ResponseEntity<KeycloakLoginResponse> keycloakResult;
        try{
            keycloakResult = restTemplate.postForEntity(uri.toString(), request, KeycloakLoginResponse.class);
            String token = keycloakResult.getBody().getToken_type() + " " + keycloakResult.getBody().getAccess_token();
            ResponseEntity<?> response = new ResponseEntity<>(token, HttpStatus.OK);
            return response;
        }
        catch (Exception e) {
            if(e.getMessage().contains("401")) {
                return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>("Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private MultiValueMap<String, String> generateLoginRequestParams(LoginRequestMapper loginRequestMapper) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("username", loginRequestMapper.getUsername());
        params.set("password", loginRequestMapper.getPassword());
        params.set("grant_type", grantType);
        params.set("client_id", clientId);
        params.set("client_secret", clientSecret);
        return params;
    }
}
