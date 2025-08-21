package org.example.capstone3.DTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CampaignDTO {
    @NotNull(message = "Campaign advertiser_id is required!")
    private Integer advertiser_id;

    @NotEmpty(message = "name cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @NotEmpty(message = "objective cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String objective;

    @NotEmpty(message = "Campaign district cannot be empty")
    private String district;

    @NotNull(message = "Campaign latitude is required!")
    private Double lat;
    @NotNull(message = "Campaign longitude is required!")
    private Double lng;

}
