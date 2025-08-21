package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
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
    @Column(columnDefinition = "varchar(255) not null")
    private String businessName;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "please enter a valid email")
    @Column(columnDefinition = "varchar(255) not null unique")
    private String email;

    @Pattern(regexp = "^\\+966\\d{9}$", message = "Phone number must be in the format +966XXXXXXXXX")
    @Column(nullable = false)
    private String phoneNumber;

    @NotEmpty(message = "operating regions cannot be empty")
    @Column(columnDefinition = "varchar(255) not null")
    private String operatingRegions;

    @NotEmpty(message = "Content Restrictions is required")
    @Column(columnDefinition = "varchar(255)")
    private String contentRestrictions;

    @Column(columnDefinition = "decimal")
    private Double ratingAvg;
    @Column(columnDefinition = "int")
    private Integer rentCount;

    @OneToMany(mappedBy = "lessor", cascade = CascadeType.ALL)
    private Set<Billboard> billboards;

    @OneToMany(mappedBy = "lessor", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;
}
