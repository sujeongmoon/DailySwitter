package com.sparta.dailyswitter.domain.comment.repository;

import static com.sparta.dailyswitter.domain.comment.entity.QComment.comment;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.post.entity.Post;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<Comment> getComment(Post post, Pageable pageable) {

		List<Comment> commentList = jpaQueryFactory
			.selectFrom(comment)
			.where(comment.post.eq(post))
			.orderBy(comment.createdAt.desc())
			.fetch();

		long totalSize = countQuery(post).fetch().get(0);

		return PageableExecutionUtils.getPage(commentList, pageable, () -> totalSize);
	}

	private JPAQuery<Long> countQuery(Post post){
		return jpaQueryFactory.select(Wildcard.count)
			.from(comment)
			.where(
				postEq(post)
			);
	}

	private BooleanExpression postEq(Post post){
		return Objects.nonNull(post) ? comment.post.eq(post) : null;
	}

}
