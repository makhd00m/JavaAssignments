package com.scaler.blogapi.users;

import com.scaler.blogapi.articles.ArticleEntity;
import com.scaler.blogapi.commons.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
    String password;            // TODO: do not save password in plain text
    @Column
    String bio;

    @ManyToMany(targetEntity = UserEntity.class, mappedBy = "following")
    List<UserEntity> followers;

    @ManyToMany(targetEntity = UserEntity.class)
    List<UserEntity> following;

    @ManyToMany(targetEntity = ArticleEntity.class, mappedBy = "likes")
    List<ArticleEntity> favouriteArticles;
}
