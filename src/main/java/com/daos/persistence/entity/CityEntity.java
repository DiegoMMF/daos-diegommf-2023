package com.daos.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false, unique = true)
    private Long cityId;

    @Column(name = "name", length = 500)
    private String cityName;

    @Column(name = "zip_code")
    private String zipCode;
}
