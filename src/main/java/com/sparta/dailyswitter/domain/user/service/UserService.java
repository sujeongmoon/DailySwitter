package com.sparta.dailyswitter.domain.user.service;

import org.springframework.stereotype.Service;

import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserResponseDto getUser(Long userId) {
		User user = findById(userId);

		return new UserResponseDto(user);
	}
}
