package com.scaler.blogapi.articles;

import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity(name = "articles")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ArticleEntity extends BaseEntity {

    @NonNull
    @Column(nullable = false, length = 150)
    String title;
    @NonNull
    @Column(nullable = false, length = 100)
    String slug;

    @Column(length = 250)
    String subtitle;
    @NonNull
    @Column(nullable = false, length = 3000)
    String body;

    // TODO: see how to implement this (without making other tables)
    // @Column
    // String[] tags;

    @NonNull
    @ManyToOne
    UserEntity author;

    @ManyToMany(targetEntity = UserEntity.class)
    @JoinTable(
            name = "articles_likes",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<UserEntity> likes;
}
