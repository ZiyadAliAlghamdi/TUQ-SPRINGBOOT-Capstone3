package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(columnDefinition = "varchar(20) not null")
    private String type;

    @Column(columnDefinition = "int not null")
    private Integer score;

    @NotEmpty(message = "status cannot be null")
    @Pattern(regexp = "opened|closed", message = "Status must be 'opened' or 'closed'")
    @Column(columnDefinition = "varchar(255) not null")
    private String status;

    @Column(columnDefinition = "varchar(255) not null")
    private String comment;

    @CreationTimestamp
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
