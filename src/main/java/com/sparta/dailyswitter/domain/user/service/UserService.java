package com.sparta.dailyswitter.domain.user.service;

import org.springframework.stereotype.Service;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.user.dto.UserInfoUpdateDto;
import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserResponseDto getUser(Long id) {
		// DB에 존재하는 User
		User user = findUser(id);

		return new UserResponseDto(user);
	}

	public UserInfoUpdateDto updateUser(Long id, UserInfoUpdateDto userInfoUpdateDto) {
		// DB에 존재하는 User
		User user = findUser(id);

		// User 정보 수정
		user.updateUserInfo(userInfoUpdateDto);

		return new UserInfoUpdateDto(user);
	}

	// Id로 User 찾기
	private User findUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}

