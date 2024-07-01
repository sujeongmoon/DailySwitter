package com.sparta.dailyswitter.domain.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import com.sparta.dailyswitter.domain.comment.entity.Comment;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
	List<Comment> findAllByOrderByCreatedAtDesc();
}
