package com.sparta.dailyswitter.domain.like.commentlike.repository;

import static com.sparta.dailyswitter.domain.like.commentlike.entity.QCommentLike.commentLike;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Comment> getCommentLikeCommentId(User user) {

		List<Comment> commentLikeCommentList = jpaQueryFactory
			.select(commentLike.id.comment)
			.from(commentLike)
			.where(commentLike.id.user.eq(user))
			.fetch();

		return commentLikeCommentList;

	}

	@Override
	public Long getUserCommentLikesCount(User user) {

		Long userCommentLikesCount = jpaQueryFactory
			.select(commentLike.count())
			.from(commentLike)
			.where(commentLike.id.user.eq(user))
			.fetchCount();

		return userCommentLikesCount;
	}
}
