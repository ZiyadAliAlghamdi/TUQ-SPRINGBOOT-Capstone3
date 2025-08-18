package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Advertiser advertiser;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<CampaignAsset> campaignAssets;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;

    @NotEmpty(message = "name cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @NotEmpty(message = "objective cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String objective;

    @NotEmpty(message = "status cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String status;
}
