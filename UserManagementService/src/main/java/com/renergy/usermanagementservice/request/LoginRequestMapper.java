package com.renergy.usermanagementservice.request;

import lombok.*;

@Data
public class LoginRequestMapper {

    @NonNull
    private String username;
    @NonNull
    private String password;

    private String grant_type;

    private String client_secret;

    private String client_id;
}
