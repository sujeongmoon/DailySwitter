package com.sparta.dailyswitter.domain.user.dto;

import com.sparta.dailyswitter.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UserInfoUpdateDto {
	private String name;
	private String email;
	private String intro;

	public UserInfoUpdateDto(User user) {
		this.name = user.getUsername();
		this.email = user.getEmail();
		this.intro = user.getIntro();
	}
}
