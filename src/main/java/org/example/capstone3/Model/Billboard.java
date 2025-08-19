package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Billboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "lessor_id", referencedColumnName = "id")
    private Lessor lessor;

    @OneToMany(mappedBy = "billboard", cascade = CascadeType.ALL)
    private Set<Booking> bookings;

    @Column(columnDefinition = "varchar(50) not null")
    private String title;

    @Column(columnDefinition = "varchar(100) not null")
    private String address;

    @Column(columnDefinition = "decimal(9,6) not null")
    private Double lat;

    @Column(columnDefinition = "decimal(9,6) not null")
    private Double lng;

    @Column(columnDefinition = "varchar(20) not null")
    private String type;

    @Column(columnDefinition = "decimal(5,2) not null")
    private Double width;

    @Column(columnDefinition = "decimal(5,2) not null")
    private Double height;

    @Column(columnDefinition = "varchar(20) not null")
    private String availabilityStatus;

    @Column(columnDefinition = "decimal(10,2) not null")
    private Double basePricePerWeek;

    @Column(columnDefinition = "decimal(3,2) not null")
    private Double ratingAvg;

    @Column(columnDefinition = "int not null")
    private Integer ratingCount;
}
