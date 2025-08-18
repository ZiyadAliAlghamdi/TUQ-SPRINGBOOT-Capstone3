package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "billboard_id", referencedColumnName = "id")
    private Billboard billboard;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;

    @NotNull(message = "start date cannot be null")
    @FutureOrPresent(message = "start date must be in the present or future")
    @Column(columnDefinition = "date not null")
    private LocalDate startDate;

    @NotNull(message = "end date cannot be null")
    @FutureOrPresent(message = "end date must be in the present or future")
    @Column(columnDefinition = "date not null")
    private LocalDate endDate;

    @NotNull(message = "price total cannot be null")
    @Column(columnDefinition = "decimal(10,2) not null")
    private Double priceTotal;

    @NotNull(message = "status cannot be null")
    @Column(columnDefinition = "varchar(20) not null")
    private String status;

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
