package com.renergy.usermanagementservice.responses;

import lombok.Data;

@Data
public class KeycloakLoginResponse {

    private String access_token;

    private int expires_in;

    private int refresh_expires_in;

    private String refresh_token;

    private String token_type;

    private boolean not_before_policy;

    private String session_state;

    private String scope;
}
