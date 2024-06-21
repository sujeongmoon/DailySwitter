package com.sparta.dailyswitter.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.user.dto.UserInfoRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserPwRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.service.UserService;
import com.sparta.dailyswitter.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "User API", description = "사용자 정보에 대한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@Operation(summary = "Get User", description = "프로필 조회 기능입니다.")
	@GetMapping
	public ResponseEntity<UserResponseDto> getUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return ResponseEntity.ok().body(userService.getUser(userDetails.getUser().getId()));
	}

	@Operation(summary = "Update User Info", description = "프로필 기본정보에 대한 수정 기능입니다.")
	@PutMapping
	public ResponseEntity<UserResponseDto> updateUserInfo(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody UserInfoRequestDto userInfoRequestDto) {

		return ResponseEntity.ok().body(userService.updateUserInfo(userDetails.getUser().getId(), userInfoRequestDto));
	}

	@Operation(summary = "Update Password", description = "사용자의 비밀번호 수정 기능입니다.")
	@PutMapping("/password")
	public ResponseEntity<UserResponseDto> updatePassword(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid UserPwRequestDto userPwRequestDto) {

		return ResponseEntity.ok().body(userService.updatePassword(userDetails.getUser().getId(), userPwRequestDto));
	}
}
