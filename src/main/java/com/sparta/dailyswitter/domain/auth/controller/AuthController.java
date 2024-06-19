package com.sparta.dailyswitter.domain.auth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.dailyswitter.domain.auth.dto.LoginRequestDto;
import com.sparta.dailyswitter.domain.auth.dto.SignupRequestDto;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestPart(value = "user") SignupRequestDto requestDto,
		@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
		return ResponseEntity.ok(userService.createUser(requestDto, file));
	}
}