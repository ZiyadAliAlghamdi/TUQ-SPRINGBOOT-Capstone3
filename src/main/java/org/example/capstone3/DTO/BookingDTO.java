package org.example.capstone3.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Setter
@Getter
@AllArgsConstructor
public class BookingDTO
{
    @NotNull(message = "Booking campaign_id is required")
    private Integer campaign_id;

    @NotNull(message = "Booking billboard_id is required")
    private Integer billboard_id;
    @NotNull(message = "start date cannot be null")
    @FutureOrPresent(message = "start date must be in the present or future")
    private LocalDate startDate;

    @NotNull(message = "end date cannot be null")
    @FutureOrPresent(message = "end date must be in the present or future")
    private LocalDate endDate;
}
