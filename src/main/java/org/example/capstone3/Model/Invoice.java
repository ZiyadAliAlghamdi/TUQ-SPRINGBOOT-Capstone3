package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice {
    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Booking booking;


    //todo:invoice InDto
    @NotEmpty(message = "cardName cannot be empty")
    @Column(nullable = false)
    private String cardName;

    @NotEmpty(message = "cardNumber cannot be empty")
    @Column(nullable = false)
    private String cardNumber;

    @NotEmpty(message = "cardCvc cannot be empty")
    @Column(nullable = false)
    private String cardCvc;

    @NotEmpty(message = "cardMonth cannot be empty")
    @Column(nullable = false)
    private String cardMonth;

    @NotEmpty(message = "cardYear cannot be null")
    @Column(nullable = false)
    private String cardYear;




    @NotNull(message = "amount cannot be null")
    @Column(nullable = false)
    private Double amount;

    @NotEmpty(message = "currency cannot be empty")
    @Column(columnDefinition = "varchar(5) not null")
    private String currency;

    @NotNull(message = "due date cannot be null")
    @FutureOrPresent(message = "due date must be in the present or future")
    @Column(columnDefinition = "date not null")
    private LocalDate dueDate;

    @NotEmpty(message = "status cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String status;  //todo from moyaser
}
