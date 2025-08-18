package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Billboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer lessorId;

    @NotEmpty(message = "title cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String title;

    @NotEmpty(message = "address cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String address;

    @NotNull(message = "latitude cannot be null")
    @Column(columnDefinition = "decimal(9,6) not null")
    private Double lat;

    @NotNull(message = "longitude cannot be null")
    @Column(columnDefinition = "decimal(9,6) not null")
    private Double lng;

    @NotEmpty(message = "type cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String type;

    @NotNull(message = "width cannot be null")
    @Column(columnDefinition = "decimal(5,2) not null")
    private Double width;

    @NotNull(message = "height cannot be null")
    @Column(columnDefinition = "decimal(5,2) not null")
    private Double height;

    @NotEmpty(message = "availability status cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String availabilityStatus;

    @NotNull(message = "base price per week cannot be null")
    @Column(columnDefinition = "decimal(10,2) not null")
    private Double basePricePerWeek;

    @NotNull(message = "rating average cannot be null")
    @Column(columnDefinition = "decimal(3,2) not null")
    private Double ratingAvg;

    @NotNull(message = "rating count cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer ratingCount;
}
