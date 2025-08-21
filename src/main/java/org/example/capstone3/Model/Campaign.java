package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private Advertiser advertiser;


    @OneToMany(mappedBy = "campaign")
    private Set<Booking> bookings;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<Feedback> feedbacks;

    @Column(columnDefinition = "varchar(50) not null")
    private String name;


    @Column(columnDefinition = "varchar(100) not null")
    private String objective;

    @Column(columnDefinition = "varchar(100) not null")
    private String district;


    @Column(columnDefinition = "decimal(9,6) not null")
    private Double lat;

    @Column(columnDefinition = "decimal(9,6) not null")
    private Double lng;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<CampaignAsset> campaignAssets;
}
