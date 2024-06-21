package com.sparta.dailyswitter.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserPwRequestDto {
	@Schema(description = "현재 비밀번호")
	@Pattern(
		regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$",
		message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."
	)
	private String currentPassword;
	@Schema(description = "새로운 비밀번호")
	@Pattern(
		regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$",
		message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다."
	)
	private String newPassword;
}
