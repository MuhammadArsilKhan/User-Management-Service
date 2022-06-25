package com.renergy.usermanagementservice.responses;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class DefaultResponse {

    @NonNull
    private String code;
    @NonNull
    private String message;

    private Long createdId;
}
