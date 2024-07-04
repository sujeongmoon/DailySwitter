package com.sparta.dailyswitter.domain.like.postlike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import com.sparta.dailyswitter.domain.like.postlike.entity.PostLike;
import com.sparta.dailyswitter.domain.like.postlike.entity.PostLikeId;

@RepositoryDefinition(domainClass = PostLike.class, idClass = PostLikeId.class)
public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId>, PostLikeRepositoryCustom {
}
