package com.sparta.dailyswitter.domain.post.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {
	private final String title;
	private final String contents;
	private final String userId;
	private Long postLikes;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	@Builder
	public PostResponseDto(String title, String contents, String userId, Long postLikes, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this.title = title;
		this.contents = contents;
		this.userId = userId;
		this.postLikes = postLikes;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
