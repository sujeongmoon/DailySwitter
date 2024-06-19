package com.sparta.dailyswitter.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.dailyswitter.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
