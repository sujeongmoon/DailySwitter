package com.sparta.dailyswitter.domain.like.commentlike.repository;

import java.util.List;

import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.user.entity.User;

public interface CommentLikeRepositoryCustom {
	List<Comment> getCommentLikeCommentId(User user);
}
