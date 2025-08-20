package org.example.capstone3.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
public class BillboardDTO {


    @NotNull(message = "Lessor_Id Cannot Be Empty")
    private Integer lessor_id;

    @NotEmpty(message = "title cannot be empty")
    private String title;

    @NotEmpty(message = "address cannot be empty")
    private String address;

    @NotNull(message = "latitude cannot be null")
    private Double lat;

    @NotNull(message = "longitude cannot be null")
    private Double lng;

    @NotEmpty(message = "type cannot be empty")
    private String type;

    @NotNull(message = "width cannot be null")
    private Double width;

    @NotNull(message = "height cannot be null")
    private Double height;

    @NotEmpty(message = "availability status cannot be empty")
    private String availabilityStatus;

    @NotNull(message = "base price per week cannot be null")
    private Double basePricePerWeek;

}
