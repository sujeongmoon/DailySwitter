package com.sparta.dailyswitter.domain.comment.service;

import static com.sparta.dailyswitter.common.exception.ErrorCode.COMMENT_NOT_FOUND;
import static com.sparta.dailyswitter.common.exception.ErrorCode.COMMENT_NOT_USER;
import static com.sparta.dailyswitter.common.exception.ErrorCode.COMMENT_SAME_USER;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
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

	@Transactional
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

	public List<CommentResponseDto> getComment(Long postId) {
		List<Comment> commentList = commentRepository.findByPostId(postId);
		if (commentList.isEmpty()) {
			throw new CustomException(COMMENT_NOT_FOUND);
		}
		List<CommentResponseDto> commentResponseDtoList = commentList.stream()
			.map(comment -> CommentResponseDto.builder()
				.comment(comment)
				.build())
			.toList();
		return commentResponseDtoList;
	}

	@Transactional
	public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestDto, User user) {
		Post post = postService.findById(postId);
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

		checkCommentPostNotFound(comment, post);
		checkCommentUserNotFound(comment, user);
		comment.updateComment(requestDto);

		return CommentResponseDto.builder()
			.comment(comment)
			.build();
	}

	@Transactional
	public void deleteComment(Long postId, Long commentId, User user) {
		Post post = postService.findById(postId);
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));

		checkCommentPostNotFound(comment, post);
		checkCommentUserNotFound(comment, user);
		commentRepository.delete(comment);
	}

	public Comment findById(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND)
		);
	}

	public void checkCommentPostNotFound(Comment comment, Post post) {
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new CustomException(COMMENT_NOT_FOUND);
		}
	}

	public void checkCommentUserNotFound(Comment comment, User user) {
		if (!comment.getUser().getId().equals(user.getId())) {
			throw new CustomException(COMMENT_NOT_USER);
		}
	}

	public void checkCommentUserFound(Comment comment, User user) {
		if (comment.getUser().getId().equals(user.getId())) {
			throw new CustomException(COMMENT_SAME_USER);
		}
	}
}
