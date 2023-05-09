package com.scaler.blogapi.articles;

import com.scaler.blogapi.commons.BaseEntity;
import com.scaler.blogapi.users.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "articles")
public class ArticleEntity extends BaseEntity {

    @Column(nullable = false, length = 150)
    String title;
    @Column(nullable = false, length = 100)
    String slug;
    @Column(length = 250)
    String subtitle;
    @Column(nullable = false, length = 3000)
    String body;

    @ManyToOne
    UserEntity author;
}
