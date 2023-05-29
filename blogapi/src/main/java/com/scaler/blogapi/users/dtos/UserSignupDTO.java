package com.scaler.blogapi.users.dtos;

import lombok.*;

@Data
public class UserSignupDTO {
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
