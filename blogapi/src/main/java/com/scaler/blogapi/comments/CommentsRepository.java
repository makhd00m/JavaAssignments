package com.scaler.blogapi.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, UUID> {
}
