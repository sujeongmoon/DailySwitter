package com.sparta.dailyswitter.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.post.dto.PostRequestDto;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.repository.PostRepository;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private PostRepository postRepository;
	private UserRepository userRepository;

	@Transactional
	public PostResponseDto createPost(PostRequestDto requestDto, String username) {
		User user = userRepository.findByUserId(username).orElseThrow(
			() -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
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
	public PostResponseDto updatePost(Long postId, PostRequestDto requestDto) {
		Post post = postRepository.findById(postId).orElseThrow(
			() -> new IllegalArgumentException("Post not found")
		);
		post.update(requestDto.getTitle(), requestDto.getContents());
		postRepository.save(post);
		return convertToDto(post);
	}

	@Transactional
	public void deletePost(Long postId) {
		postRepository.deleteById(postId);
	}

	public PostResponseDto getPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(
			() -> new CustomException(ErrorCode.POST_NOT_FOUND)
		);
		return convertToDto(post);
	}

	public Post findById(Long postId) {
		return postRepository.findById(postId).orElseThrow(
			() -> new IllegalArgumentException("해당 포스트가 존재하지 않습니다.")
		);
	}

	private PostResponseDto convertToDto(Post post) {
		return PostResponseDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.contents(post.getContents())
			.userId(post.getUser().getUserId())
			.email(post.getUser().getEmail())
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())
			.build();
	}
}
