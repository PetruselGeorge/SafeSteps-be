package com.example.SafeStep_be.mapper;

import com.example.SafeStep_be.data.access.layer.entities.PackageEntity;
import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import com.example.SafeStep_be.data.access.layer.enums.PackageType;
import com.example.SafeStep_be.dto.RegistrationUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "aPackage", source = "packageType", qualifiedByName = "mapPackageTypeToPackageEntity")
    UserEntity toEntity(RegistrationUserDto registrationUserDto, String encodedPassword, String packageType);

    @Named("mapPackageTypeToPackageEntity")
    default PackageEntity mapPackageTypeToPackageEntity(PackageType packageType) {
        if (packageType == null) {
            return null;
        }
        return PackageEntity.builder()
                .packageType(packageType)
                .build();
    }
}
