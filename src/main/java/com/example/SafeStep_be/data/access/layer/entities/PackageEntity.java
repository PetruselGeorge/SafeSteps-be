package com.example.SafeStep_be.data.access.layer.entities;

import com.example.SafeStep_be.data.access.layer.enums.PackageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "packages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "package_id")
    private UUID id;

    @Column(name = "package_type", nullable = false, length = 15)
    @Enumerated(value = EnumType.STRING)
    private PackageType packageType;

    @Column(name = "expire_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime expire_date;

    @OneToOne(mappedBy = "aPackage", cascade = CascadeType.ALL)
    private UserEntity user;

}
