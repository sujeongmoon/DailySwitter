package com.sparta.dailyswitter.domain.like.postlike.repository;

import java.util.List;

import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.user.entity.User;

public interface PostLikeRepositoryCustom {
	List<Post> getPostLikePostId(User user);

	Long getUserPostLikesCount(User user);
}
