package com.renergy.usermanagementservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HelpEmailMapper {

    private String name;

    private String phoneNumber;

    private String city;

    private String email;

    private String message;
}
