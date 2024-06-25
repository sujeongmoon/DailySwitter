package com.sparta.dailyswitter.domain.admin.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.comment.dto.CommentRequestDto;
import com.sparta.dailyswitter.domain.comment.dto.CommentResponseDto;
import com.sparta.dailyswitter.domain.comment.service.CommentService;
import com.sparta.dailyswitter.domain.post.dto.PostRequestDto;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.domain.user.dto.UserInfoRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.dto.UserRoleChangeRequestDto;
import com.sparta.dailyswitter.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	private final UserService userService;
	private final PostService postService;
	private final CommentService commentService;

	// 사용자 관리
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {
		return ResponseEntity.ok().body(userService.getAllUsers());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserResponseDto> updateUser(
		@PathVariable Long userId,
		@RequestBody UserInfoRequestDto userInfoRequestDto) {

		return ResponseEntity.ok().body(userService.updateUserInfo(userId, userInfoRequestDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> deleteUser(
		@PathVariable Long userId) {

		userService.deleteUser(userId);
		return ResponseEntity.ok("사용자가 삭제되었습니다.");
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/users/{userId}/role")
	public ResponseEntity<UserResponseDto> updateUserRole(
		@PathVariable Long userId,
		@RequestBody UserRoleChangeRequestDto userRoleChangeRequestDto) {
		;
		return ResponseEntity.ok().body(userService.updateUserRole(userId, userRoleChangeRequestDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/users/{userId}/block")
	public ResponseEntity<UserResponseDto> toggleBlockStatus(
		@PathVariable Long userId) {

		return ResponseEntity.ok().body(userService.toggleBlockStatus(userId));
	}

	// 게시글 관리
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/posts")
	public ResponseEntity<Page<PostResponseDto>> getAllPosts(
		@RequestParam(defaultValue = "0") int page) {

		Pageable pageable = PageRequest.of(page, 5);
		return ResponseEntity.ok().body(postService.getAllPosts(pageable));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/posts/{postId}")
	public ResponseEntity<?> updatePost(
		@PathVariable Long postId,
		@RequestBody PostRequestDto requestDto) {

		PostResponseDto responseDto = postService.AdminUpdatePost(postId, requestDto);
		return ResponseEntity.ok().body(responseDto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<String> deletePost(
		@PathVariable Long postId) {

		postService.AdminDeletePost(postId);
		return ResponseEntity.ok("게시물이 삭제되었습니다.");
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/posts/{postId}/pin")
	public ResponseEntity<PostResponseDto> pinPost(
		@PathVariable Long postId) {

		PostResponseDto pinnedPost = postService.togglePinPost(postId);
		return ResponseEntity.ok().body(pinnedPost);
	}

	// 댓글 관리
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/comments")
	public ResponseEntity<List<CommentResponseDto>> getAllComments() {
		return ResponseEntity.ok().body(commentService.getAllComments());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/comments/{commentId}")
	public ResponseEntity<CommentResponseDto> adminUpdateComment(
		@PathVariable Long commentId,
		@RequestBody @Valid CommentRequestDto requestDto) {
		return ResponseEntity.status(HttpStatus.OK)
			.body(commentService.adminUpdateComment(commentId, requestDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<String> deleteComment(
		@PathVariable Long commentId) {
		commentService.adminDeleteComment(commentId);
		return ResponseEntity.ok("댓글이 삭제되었습니다.");
	}
}
