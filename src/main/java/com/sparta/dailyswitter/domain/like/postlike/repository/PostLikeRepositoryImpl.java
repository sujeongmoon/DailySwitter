package com.sparta.dailyswitter.domain.like.postlike.repository;

import static com.sparta.dailyswitter.domain.like.postlike.entity.QPostLike.postLike;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostLikeRepositoryImpl implements PostLikeRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Post> getPostLikePostId(User user) {

		List<Post> postLikePostList = jpaQueryFactory
			.select(postLike.id.post)
			.from(postLike)
			.where(postLike.id.user.eq(user))
			.fetch();

		return postLikePostList;
	}

	@Override
	public Long getUserPostLikesCount(User user) {

		Long userPostLikesCount = jpaQueryFactory
			.select(postLike.count())
			.from(postLike)
			.where(postLike.id.user.eq(user))
			.fetchCount();

		return userPostLikesCount;
	}
}
