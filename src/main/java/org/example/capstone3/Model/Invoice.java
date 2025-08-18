package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer bookingId;

    @NotNull(message = "amount due cannot be null")
    @Column(columnDefinition = "decimal(10,2) not null")
    private Double amountDue;

    @NotEmpty(message = "currency cannot be empty")
    @Column(columnDefinition = "varchar(5) not null")
    private String currency;

    @NotNull(message = "due date cannot be null")
    @FutureOrPresent(message = "due date must be in the present or future")
    @Column(columnDefinition = "date not null")
    private LocalDate dueDate;

    @NotEmpty(message = "status cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String status;
}
