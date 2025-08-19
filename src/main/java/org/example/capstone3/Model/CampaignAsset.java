package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CampaignAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonIgnore
    private Campaign campaign;

    @NotEmpty(message = "asset type cannot be empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String assetType;

    @NotEmpty(message = "mime type cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String mimeType;

    @NotEmpty(message = "filename cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String fileName;

    @Lob
    @Column(nullable = false)
    private byte[] fileContent;

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
