package com.example.SafeStep_be.data.access.layer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "trail_coordinates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrailCoordinateEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trail_id", nullable = false, foreignKey = @ForeignKey(name = "fk_trail_coordinates_trail_id"))
    private TrailEntity trail;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(name = "position_order", nullable = false)
    private int positionOrder;

    @Column(name = "segment_index", nullable = false)
    private int segmentIndex;
}
