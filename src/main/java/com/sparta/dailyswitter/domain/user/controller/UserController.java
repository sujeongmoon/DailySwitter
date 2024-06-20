package com.sparta.dailyswitter.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@GetMapping
	public UserResponseDto getUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return userService.getUser(userDetails.getUser().getUserId());
	}
}
