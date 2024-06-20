package com.sparta.dailyswitter.domain.auth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.auth.dto.LoginRequestDto;
import com.sparta.dailyswitter.domain.auth.dto.LoginResponseDto;
import com.sparta.dailyswitter.domain.auth.dto.SignupRequestDto;
import com.sparta.dailyswitter.domain.auth.service.AuthService;
import com.sparta.dailyswitter.domain.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody SignupRequestDto requestDto) throws IOException {
		return ResponseEntity.ok(authService.signup(requestDto));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
		return ResponseEntity.ok(authService.login(requestDto));
	}
}