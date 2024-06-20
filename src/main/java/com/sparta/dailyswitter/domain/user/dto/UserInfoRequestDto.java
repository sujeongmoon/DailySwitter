package com.sparta.dailyswitter.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserInfoRequestDto {
	private String username;
	private String intro;
}
