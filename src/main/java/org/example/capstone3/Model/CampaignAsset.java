package org.example.capstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CampaignAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer campaignId;

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
    @Column(columnDefinition = "longblob")
    private byte[] blob;

    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
