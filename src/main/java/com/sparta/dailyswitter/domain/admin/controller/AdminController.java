package com.sparta.dailyswitter.domain.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.comment.service.CommentService;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.domain.user.dto.UserInfoRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.dto.UserRoleChangeRequestDto;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.service.UserService;

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
	public List<UserResponseDto> getAllUsers() {
		return userService.getAllUsers();
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
	public ResponseEntity<Void> deleteUser(
		@PathVariable Long userId) {

		userService.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PatchMapping("/users/{userId}/role")
	public ResponseEntity<UserResponseDto> changeUserRole(
		@PathVariable Long userId,
		@RequestBody UserRoleChangeRequestDto userRoleChangeRequestDto) {
;
		return ResponseEntity.ok().body(userService.updateUserRole(userId, userRoleChangeRequestDto));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PatchMapping("/users/{userId}/block")
	public ResponseEntity<UserResponseDto> toggleBlockStatus(
		@PathVariable Long userId) {

		return ResponseEntity.ok().body(userService.toggleBlockStatus(userId));
	}
}
