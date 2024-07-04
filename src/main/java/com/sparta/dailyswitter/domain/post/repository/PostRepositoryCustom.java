package com.sparta.dailyswitter.domain.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.dailyswitter.domain.post.entity.Post;

public interface PostRepositoryCustom {
	Page<Post> getPost(List<Post> postLikesPostId,  Pageable pageable);
}
