package com.renergy.usermanagementservice.user;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private String contactNumber;

}
