package com.scaler.blogapi.users.dtos;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserUpdateDTO {
    private String email;
    private String password;
    private String bio;
}
