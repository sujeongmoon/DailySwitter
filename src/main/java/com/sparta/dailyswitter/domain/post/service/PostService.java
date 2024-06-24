package com.sparta.dailyswitter.domain.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.follow.service.FollowService;
import com.sparta.dailyswitter.domain.post.dto.PostRequestDto;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.repository.PostRepository;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;
import com.sparta.dailyswitter.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final FollowService followService;


	@Transactional
	public PostResponseDto createPost(PostRequestDto requestDto, String username) {
		User user = userRepository.findByUserId(username).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);
		Post post = Post.builder()
			.title(requestDto.getTitle())
			.contents(requestDto.getContents())
			.user(user)
			.build();
		Post savedPost = postRepository.save(post);
		return convertToDto(savedPost);
	}

	@Transactional
	public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, String username) {
		Post post = postRepository.findById(postId).orElseThrow(
			() -> new CustomException(ErrorCode.POST_NOT_FOUND)
		);

		if (!post.getUser().getUserId().equals(username)) {
			throw new CustomException(ErrorCode.POST_NOT_USER);
		}

		post.update(requestDto.getTitle(), requestDto.getContents());
		return convertToDto(post);
	}

	@Transactional
	public void deletePost(Long postId, String username) {

		Post post = postRepository.findById(postId).orElseThrow(
			() -> new CustomException(ErrorCode.POST_NOT_FOUND)
		);

		if (!post.getUser().getUserId().equals(username)) {
			throw new CustomException(ErrorCode.POST_NOT_USER);
		}
		postRepository.delete(post);
	}

	public PostResponseDto getPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(
			() -> new CustomException(ErrorCode.POST_NOT_FOUND)
		);
		return convertToDto(post);
	}

	@Transactional(readOnly = true)
	public Page<PostResponseDto> getAllPosts(Pageable pageable) {
		return postRepository.findAllByOrderByCreatedAtDesc(pageable)
			.map(this::convertToDto);
	}

	@Transactional(readOnly = true)
	public Page<PostResponseDto> getFollowedPosts(User followerUser, Pageable pageable) {
		List<User> follows = followService.getFollows(followerUser);
		return postRepository.findByUserInOrderByCreatedAtDesc(follows, pageable).map(this::convertToDto);
	}

	public Post findById(Long postId) {
		return postRepository.findById(postId).orElseThrow(
			() -> new CustomException(ErrorCode.POST_NOT_FOUND)
		);
	}

	private PostResponseDto convertToDto(Post post) {
		return PostResponseDto.builder()
			.title(post.getTitle())
			.contents(post.getContents())
			.userId(post.getUser().getUserId())
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())
			.build();
	}
}
