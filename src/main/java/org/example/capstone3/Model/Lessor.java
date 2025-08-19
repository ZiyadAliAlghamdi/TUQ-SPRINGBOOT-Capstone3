package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lessor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "business name cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String businessName;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "please enter a valid email")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @NotEmpty(message = "operating regions cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String operatingRegions;

    @Column(columnDefinition = "varchar(100)")
    private String contentRestrictions;

    @NotNull(message = "rating average cannot be null")
    @Column(columnDefinition = "decimal(3,2) not null")
    private Double ratingAvg;

    @NotNull(message = "rent count cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer rentCount;

    @OneToMany(mappedBy = "lessor", cascade = CascadeType.ALL)
    private Set<Billboard> billboards;

    @OneToMany(mappedBy = "lessor", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;
}
