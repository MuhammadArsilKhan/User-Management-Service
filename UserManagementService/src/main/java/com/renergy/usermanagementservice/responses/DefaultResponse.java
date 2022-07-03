package com.renergy.usermanagementservice.responses;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefaultResponse {

    @NonNull
    private String code;
    @NonNull
    private String message;

    private String createdId;
}
