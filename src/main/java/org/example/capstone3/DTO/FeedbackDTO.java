package org.example.capstone3.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.capstone3.Model.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class FeedbackDTO {


    private Integer advertiser_id;

    private Integer lessor_id;

    private Integer campaign_id;

    private Integer booking_id;

    @NotEmpty(message = "type cannot be empty")
    @Pattern(regexp = "^(complain|suggestion)$")
    private String type;

    @NotNull(message = "score cannot be null")
    private Integer score;

    @NotEmpty(message = "comment cannot be empty")
    private String comment;


}
