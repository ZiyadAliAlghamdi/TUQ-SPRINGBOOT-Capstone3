package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private Campaign campaign;

    @ManyToOne
    @JsonIgnore
    private Billboard billboard;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Invoice invoices;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;


    @Column(columnDefinition = "date not null")
    private LocalDate startDate;

    @Column(columnDefinition = "date not null")
    private LocalDate endDate;

    private OffsetDateTime acceptedAt;


    @Column(columnDefinition = "decimal(10,2) not null")
    private Double priceTotal;

    @Column(columnDefinition = "varchar(50) not null")
    private String status;

    @CreationTimestamp
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
