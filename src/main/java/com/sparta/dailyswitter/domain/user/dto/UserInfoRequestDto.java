package com.sparta.dailyswitter.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserInfoRequestDto {
	@Schema(description = "사용자 이름")
	private String username;
	@Schema(description = "사용자 소개글")
	private String intro;
}
