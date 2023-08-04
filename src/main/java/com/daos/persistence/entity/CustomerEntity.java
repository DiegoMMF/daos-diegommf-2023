package com.daos.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(
                name = "customer_id",
                nullable = false,
                unique = true
        )
        private Long customerId;

        @Column(
                name = "dni",
                nullable = false,
                unique = true
        )
        private Long customerDNI;

        @Column(nullable = false, name = "first_name")
        private String firstName;

        @Column(nullable = false, name = "last_name")
        private String lastName;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(name = "phone_number")
        private Long phoneNumber;

        @Column(
                name = "birth_date",
                columnDefinition = "DATE",
                nullable = false
        )
        private LocalDateTime birthDate;

        @Column(name = "passport_number")
        private String passportNumber;

        @Column(name = "passport_expiration_date")
        private LocalDateTime passportExpirationDate;

        @ManyToOne
        @JoinColumn(
                name = "address",
                referencedColumnName = "address_id"
        )
        private AddressEntity address;
}
