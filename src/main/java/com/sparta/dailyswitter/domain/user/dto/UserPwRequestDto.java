package com.sparta.dailyswitter.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserPwRequestDto {
	@Schema(description = "현재 비밀번호")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,15}$", message = "잘못된 비밀번호 형식입니다.")
	private String currentPassword;
	@Schema(description = "새로운 비밀번호")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,15}$", message = "잘못된 비밀번호 형식입니다.")
	private String newPassword;
}
