package com.sparta.dailyswitter.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserPwRequestDto {
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,15}$", message = "잘못된 비밀번호 형식입니다.")
	private String currentPassword;
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{8,15}$", message = "잘못된 비밀번호 형식입니다.")
	private String newPassword;
}
