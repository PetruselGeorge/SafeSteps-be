package com.example.SafeStep_be.data.access.layer.Entity;

import com.example.SafeStep_be.data.access.layer.Entity.entities.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PackageRepository extends JpaRepository<PackageEntity, UUID> {
}
