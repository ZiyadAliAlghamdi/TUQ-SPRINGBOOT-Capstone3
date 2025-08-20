package org.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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


    @NotEmpty(message = "filename cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String fileName;

    @Lob
    @Column(columnDefinition = "LONGBLOB",nullable = false)
    private byte[] fileContent;

    @CreationTimestamp
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime createdAt;
}
