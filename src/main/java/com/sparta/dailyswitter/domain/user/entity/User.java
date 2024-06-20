package com.sparta.dailyswitter.domain.user.entity;

import com.sparta.dailyswitter.common.util.Timestamped;
import com.sparta.dailyswitter.domain.user.dto.UserInfoUpdateDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Column(name = "user_id", length = 10)
	private String userId;

	@NotBlank
	@Column(name = "user_name")
	private String username;

	@NotBlank
	@Column(name = "password", length = 15)
	private String password;

	@NotBlank
	@Column(name = "email")
	private String email;

	@NotBlank
	@Column(name = "intro")
	private String intro;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;

	public void updateUserInfo(UserInfoUpdateDto userInfoUpdateDto) {
		this.username = userInfoUpdateDto.getName();
		this.email = userInfoUpdateDto.getEmail();
		this.intro = userInfoUpdateDto.getIntro();
	}

	public boolean isExist() {
		return this.role == UserRoleEnum.USER;
	}

	public void updateRefresh(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}

