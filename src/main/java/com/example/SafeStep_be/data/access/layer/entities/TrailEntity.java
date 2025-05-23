package com.example.SafeStep_be.data.access.layer.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "trails")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrailEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "distance_km", nullable = false, precision = 5, scale = 2)
    private BigDecimal distanceKm;

    @Column(name = "difficulty", nullable = false, length = 50)
    private String difficulty;

    @Lob
    @Column(name = "main_image")
    private byte[] mainImage;

    @OneToMany(mappedBy = "trail", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TrailImageEntity> images = new ArrayList<>();

    @OneToMany(mappedBy = "trail", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("positionOrder ASC")
    private List<TrailCoordinateEntity> coordinates = new ArrayList<>();

}
