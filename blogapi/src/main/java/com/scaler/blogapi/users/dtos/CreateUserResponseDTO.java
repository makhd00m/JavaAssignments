package com.scaler.blogapi.users.dtos;

import com.scaler.blogapi.users.UserEntity;
import jakarta.annotation.Nullable;
import lombok.*;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateUserResponseDTO {
    @NonNull
    String username;
    @NonNull
    String email;
    @Nullable
    String bio;

    String token;
}
