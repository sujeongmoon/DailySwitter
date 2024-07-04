package com.sparta.dailyswitter.domain.post.repository;

import static com.sparta.dailyswitter.domain.post.entity.QPost.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.dailyswitter.domain.post.entity.Post;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<Post> getPost(List<Post> postLikesPostId, Pageable pageable) {
		List<Post> postList = jpaQueryFactory
			.selectFrom(post)
			.where(post.in(postLikesPostId))
			.fetch();

		return PageableExecutionUtils.getPage(postList, pageable, () -> postLikesPostId.size());
	}
}
