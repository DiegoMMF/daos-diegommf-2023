package com.daos.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "address_id",
            nullable = false,
            unique = true
    )
    private Long addressId;

    @Column(name = "street")
    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JoinColumn(
            name = "city",
            referencedColumnName = "city_id",
            nullable = false
    )
    private CityEntity city;
}
