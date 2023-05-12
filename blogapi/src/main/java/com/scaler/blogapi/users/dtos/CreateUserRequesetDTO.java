package com.scaler.blogapi.users.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserRequesetDTO {
    @NonNull
    String username;
    @NonNull
    String email;
    @NonNull
    String password;
}
