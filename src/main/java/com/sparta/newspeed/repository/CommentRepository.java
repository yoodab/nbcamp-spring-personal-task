package com.sparta.newspeed.repository;

import com.sparta.newspeed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByPeedIdAndId(Long newspeedId, Long commentId);
}
