package com.sparta.dailyswitter.domain.comment.service;

import org.springframework.stereotype.Service;

import com.sparta.dailyswitter.domain.comment.dto.CommentRequestDto;
import com.sparta.dailyswitter.domain.comment.dto.CommentResponseDto;
import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.comment.repository.CommentRepository;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final PostService postService;
	private final CommentRepository commentRepository;

	public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
		Post post = postService.findById(postId);
		Comment comment = Comment.builder()
			.user(user)
			.post(post)
			.requestDto(requestDto)
			.build();

		commentRepository.save(comment);
		return CommentResponseDto.builder()
			.comment(comment)
			.build();
	}
}
