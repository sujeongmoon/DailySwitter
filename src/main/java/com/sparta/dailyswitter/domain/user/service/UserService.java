package com.sparta.dailyswitter.domain.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.comment.dto.CommentResponseDto;
import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.comment.service.CommentService;
import com.sparta.dailyswitter.domain.like.commentlike.service.CommentLikeService;
import com.sparta.dailyswitter.domain.like.postlike.service.PostLikeService;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.domain.user.dto.UserInfoRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserPwRequestDto;
import com.sparta.dailyswitter.domain.user.dto.UserResponseDto;
import com.sparta.dailyswitter.domain.user.dto.UserRoleChangeRequestDto;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PostLikeService postLikeService;
	private final PostService postService;
	private final CommentLikeService commentLikeService;
	private final CommentService commentService;

	public UserResponseDto getUser(Long id) {
		User user = findUser(id);

		return new UserResponseDto(user);
	}

	public List<UserResponseDto> getAllUsers() {
		List<User> users = userRepository.findAll();

		return users.stream()
			.map(UserResponseDto::new)
			.toList();
	}

	public UserResponseDto updateUserInfo(Long id, UserInfoRequestDto userInfoRequestDto) {
		User user = findUser(id);

		user.updateUserInfo(userInfoRequestDto);
		userRepository.save(user);

		return new UserResponseDto(user);
	}

	public UserResponseDto updatePassword(Long id, UserPwRequestDto userPwRequestDto) {
		User user = findUser(id);

		if (!passwordEncoder.matches(userPwRequestDto.getCurrentPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
		}

		if (user.isPasswordInHistory(userPwRequestDto.getNewPassword(), passwordEncoder)) {
			throw new CustomException(ErrorCode.DUPLICATE_PASSWORD);
		}

		user.updatePassword(passwordEncoder.encode(userPwRequestDto.getNewPassword()));
		userRepository.save(user);

		return new UserResponseDto(user);
	}

	public UserResponseDto updateUserRole(Long id, UserRoleChangeRequestDto userRoleChangeRequestDto) {
		User user = findUser(id);
		user.updateStatus(userRoleChangeRequestDto.getRole());
		userRepository.save(user);

		return new UserResponseDto(user);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public Page<PostResponseDto> getUserPostLikes(User user, Pageable pageable) {

		List<Post> postLikesPostId = postLikeService.getUserPostLikesPostId(user);
		Page<PostResponseDto> userLikePostList = postService.getPostLikes(postLikesPostId, pageable);
		return userLikePostList;
	}

	public Page<CommentResponseDto> getUserCommentLikes(User user, Pageable pageable) {

		List<Comment> commentLikesCommentId = commentLikeService.getUserCommentLikesCommentId(user);
		Page<Comment> userLikeCommentList = commentService.getCommentLikes(commentLikesCommentId, pageable);
		Page<CommentResponseDto> commentResponseDtoList = userLikeCommentList.map(CommentResponseDto::new);
		return commentResponseDtoList;
	}

	public Long getUserPostLikesCount(User user) {
		Long userPostLikesCount = postLikeService.getUserPostLikesCount(user);
		return userPostLikesCount;
	}

	public Long getUserCommentLikesCount(User user) {
		Long userCommentLikesCount = commentLikeService.getUserCommentLikesCount(user);
		return userCommentLikesCount;
	}

	public UserResponseDto toggleBlockStatus(Long id) {
		User user = findUser(id);
		user.toggleBlock();
		userRepository.save(user);

		return new UserResponseDto(user);
	}

	private User findUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
	}
}

