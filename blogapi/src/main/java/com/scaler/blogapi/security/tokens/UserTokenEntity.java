package com.scaler.blogapi.security.tokens;

import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.users.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Entity(name = "user_tokens")
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserTokenEntity extends BaseEntity {

    @ManyToOne
    UserEntity user;

    Date expiresAt;
}
