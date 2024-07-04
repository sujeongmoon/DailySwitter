package com.sparta.dailyswitter.domain.like.commentlike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import com.sparta.dailyswitter.domain.like.commentlike.entity.CommentLike;
import com.sparta.dailyswitter.domain.like.commentlike.entity.CommentLikeId;

@RepositoryDefinition(domainClass = CommentLike.class, idClass = CommentLikeId.class)
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId>, CommentLikeRepositoryCustom {

}
