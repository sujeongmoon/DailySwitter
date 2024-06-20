package com.sparta.dailyswitter.domain.user.dto;

import com.sparta.dailyswitter.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
	private String userId;
	private String username;
	private String email;
	private String intro;

	public UserResponseDto(User user) {
		this.userId = user.getUserId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.intro = user.getIntro();
	}
}
