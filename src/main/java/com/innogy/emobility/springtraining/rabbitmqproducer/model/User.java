package com.innogy.emobility.springtraining.rabbitmqproducer.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {

    private String username;
    private String firstname;
    private String lastname;
    private LocalDate birthday;

}
