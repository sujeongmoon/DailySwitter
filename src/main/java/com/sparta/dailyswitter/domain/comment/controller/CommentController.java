package com.sparta.dailyswitter.domain.comment.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.comment.dto.CommentRequestDto;
import com.sparta.dailyswitter.domain.comment.dto.CommentResponseDto;
import com.sparta.dailyswitter.domain.comment.service.CommentService;
import com.sparta.dailyswitter.security.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	private static final int COMMENT_PAGE_SIZE = 5;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId,
		@RequestBody @Valid CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.createComment
			(postId, requestDto, userDetails.getUser()));
	}

	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<Page<CommentResponseDto>> getComment(@PathVariable Long postId,
		@RequestParam(defaultValue = "0") int page) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		Pageable pageable = PageRequest.of(page, COMMENT_PAGE_SIZE, sort);
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.getComment(postId, pageable));
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId,
		@PathVariable Long commentId,
		@RequestBody @Valid CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.updateComment(postId, commentId, requestDto, userDetails.getUser()));
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long postId,
		@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		commentService.deleteComment(postId, commentId, userDetails.getUser());
		return ResponseEntity.status(HttpStatus.OK)
			.body("댓글이 삭제되었습니다.");
	}
}
