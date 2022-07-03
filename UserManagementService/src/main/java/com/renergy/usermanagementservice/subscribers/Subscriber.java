package com.renergy.usermanagementservice.subscribers;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_subscriber")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subscriberName;
    private String city;
    private String email;
    private Date createdDate;
    private String phoneNumber;
}
