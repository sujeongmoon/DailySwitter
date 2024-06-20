package com.sparta.dailyswitter.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.user.dto.UserInfoUpdateDto;
import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.service.UserService;
import com.sparta.dailyswitter.security.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping
	public UserResponseDto getUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return userService.getUser(userDetails.getUser().getId());
	}

	@PutMapping
	public UserInfoUpdateDto updateUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody @Valid UserInfoUpdateDto userInfoUpdateDto) {

		return userService.updateUser(userDetails.getUser().getId(), userInfoUpdateDto);
	}
}
