package com.sparta.dailyswitter.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.sparta.dailyswitter.common.util.Timestamped;
import com.sparta.dailyswitter.domain.user.dto.UserInfoRequestDto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "user")
public class User extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Column(name = "login_id", nullable = false, unique = true)
	private String userId;

	@NotBlank
	@Column(name = "user_name")
	private String username;

	@NotBlank
	@Column(name = "password", nullable = false)
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

	@ElementCollection
	@CollectionTable(name = "password_history", joinColumns = @JoinColumn(name = "user_id"))
	private List<String> passwordHistory = new ArrayList<>();

	public void updateUserInfo(UserInfoRequestDto userInfoRequestDto) {
		this.username = updateField(userInfoRequestDto.getUsername(), username);
		this.intro = updateField(userInfoRequestDto.getIntro(), intro);
	}

	public void updatePassword(String newPassword) {
		if (this.passwordHistory.size() >= 4) {
			this.passwordHistory.remove(0);
		}
		this.passwordHistory.add(this.password);
		this.password = newPassword;
	}

	public boolean isPasswordInHistory(String password, PasswordEncoder passwordEncoder) {
		for (String oldPassword : passwordHistory) {
			if (passwordEncoder.matches(password, oldPassword)) {
				return true;
			}
		}
		return false;
	}

	public boolean isExist() {
		return this.role != UserRoleEnum.WITHDRAW;
	}

	public void updateRefresh(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void updateStatusSignout() {
		this.role = UserRoleEnum.WITHDRAW;
	}

	private String updateField(String newValue, String currentValue) {
		return newValue == null || newValue.isBlank() ? currentValue : newValue;
	}
}