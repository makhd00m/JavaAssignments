package com.scaler.blogapi.users.dtos;

import com.scaler.blogapi.users.UserEntity;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor(force = true)
public class UserResponseDTO {
    @NonNull
    String username;
    @NonNull
    String email;
    @Nullable
    String bio;

    String token;

    public UserResponseDTO createDTO(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.bio = userEntity.getBio();
        return this;
    }
}
