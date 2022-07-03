package com.renergy.usermanagementservice.companyEvents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_company_event")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private Date date;

}
