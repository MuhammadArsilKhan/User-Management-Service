package com.renergy.usermanagementservice.request;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class KeycloakUserRequest {

    @NonNull
    private String username;

    @NonNull
    private String FirstName;

    @NonNull
    private String LastName;

    @NonNull
    private String email;

}
