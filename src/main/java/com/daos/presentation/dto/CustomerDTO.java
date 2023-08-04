package com.daos.presentation.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNumber;
    private LocalDateTime birthDate;
    private String passport;
    private LocalDateTime passportExpirationDate;
    private Long address;
}
