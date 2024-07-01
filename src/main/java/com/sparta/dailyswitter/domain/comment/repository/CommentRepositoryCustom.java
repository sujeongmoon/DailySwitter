package com.sparta.dailyswitter.domain.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.post.entity.Post;

public interface CommentRepositoryCustom {
	Page<Comment> getComment(Post post, Pageable pageable);
}
