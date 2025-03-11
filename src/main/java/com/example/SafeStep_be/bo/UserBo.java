package com.example.SafeStep_be.bo;

import com.example.SafeStep_be.data.access.layer.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserBo extends UserDetailsService {
    void createUser(UserEntity user);
}
