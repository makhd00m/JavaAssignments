package com.scaler.blogapi.users;

import com.scaler.blogapi.commons.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "users")
public class UserEntity extends BaseEntity {
    @Column(nullable = false, unique = true, length = 30)
    String username;
    @Column(nullable = false, unique = false, length = 50)
    String email;
    @Column(nullable = false, length = 32)
    String password;                            // TODO: do not save password in plain text
    @Column
    String bio;
}
