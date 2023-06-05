package com.scaler.blogapi.users.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NonNull;

@Data
public class LoginUserRequestDTO {
    @NonNull
    String username;
    @NonNull
    String password;
}