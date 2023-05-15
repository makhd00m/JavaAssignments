package com.scaler.blogapi.users.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserSignupDTO {
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
