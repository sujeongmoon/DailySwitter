package com.sparta.dailyswitter.domain.follow.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.follow.dto.FollowRequestDto;
import com.sparta.dailyswitter.domain.follow.dto.FollowResponseDto;
import com.sparta.dailyswitter.domain.follow.service.FollowService;
import com.sparta.dailyswitter.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;

	@PostMapping
	public FollowResponseDto followUser(@RequestBody FollowRequestDto followRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		if (userDetails == null || userDetails.getUser() == null) {
			throw new CustomException(ErrorCode.INVALID_REQUEST);
		}
		followService.followUser(userDetails.getUser().getId(), followRequestDto.getFollowing_user_id());
		return new FollowResponseDto("팔로우에 추가되었습니다.");
	}

	@DeleteMapping
	public FollowResponseDto  unfollowUser(@RequestBody FollowRequestDto followRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		if (userDetails == null || userDetails.getUser() == null) {
			throw new CustomException(ErrorCode.INVALID_REQUEST);
		}
		followService.unfollowUser(userDetails.getUser().getId(), followRequestDto.getFollowing_user_id());
		return new FollowResponseDto("팔로우가 취소되었습니다.");
	}
}
