package com.example.SafeStep_be.data.access.layer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "trail_images")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrailImageEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Lob
    @Column(name = "image_blob", nullable = false)
    private byte[] imageBlob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trail_id", nullable = false, foreignKey = @ForeignKey(name = "fk_trail_trail_image_id"))
    private TrailEntity trail;
}
