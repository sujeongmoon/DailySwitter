package com.sparta.dailyswitter.domain.comment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.post.entity.Post;

public interface CommentRepositoryCustom {
	Page<Comment> getComment(Post post, Pageable pageable);
	Page<Comment> getComment(List<Comment> commentLikesCommentId, Pageable pageable);
}
