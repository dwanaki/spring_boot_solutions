package com.innogy.emobility.springtraining.rabbitmqconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{

    private String username;
    private String firstname;
    private String lastname;
    private LocalDate birthday;

}
