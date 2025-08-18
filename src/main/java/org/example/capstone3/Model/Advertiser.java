package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advertiser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "please enter a valid email")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @NotEmpty(message = "company name cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String companyName;

    @NotEmpty(message = "brand name cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String brandName;

    @NotEmpty(message = "payment method cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String paymentMethod;

    @Column(columnDefinition = "varchar(100)")
    private String notes;

    @NotNull(message = "lease count cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer leaseCount;
}
