package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private Advertiser advertiser;

    @ManyToOne
    @JsonIgnore
    private Lessor lessor;

    @ManyToOne
    @JsonIgnore
    private Campaign campaign;

    @ManyToOne
    @JsonIgnore
    private Booking booking;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL)
    private Set<FeedbackAsset> feedbackAssets;

    @NotEmpty(message = "type cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String type;

    @NotNull(message = "score cannot be null")
    @Column(columnDefinition = "int not null")
    private Integer score;

    @NotEmpty(message = "comment cannot be empty")
    @Column(columnDefinition = "varchar(255) not null")
    private String comment;

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
