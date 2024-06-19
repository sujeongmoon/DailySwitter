package com.sparta.dailyswitter.domain.user.entity;

import com.sparta.dailyswitter.common.util.Timestamped;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Column(name = "user_id")
	private String userId;

	@NotBlank
	@Column(name = "user_name")
	private String username;

	@NotBlank
	@Column(name = "password")
	private String password;

	@NotBlank
	@Column(name = "email")
	private String email;

	@NotBlank
	@Column(name = "intro")
	private String intro;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;
}