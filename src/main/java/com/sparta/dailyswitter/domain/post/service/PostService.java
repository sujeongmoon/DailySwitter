package com.sparta.dailyswitter.domain.post.service;

import org.springframework.stereotype.Service;

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

	public PostResponseDto createPost(PostRequestDto requestDto, String username) {
		// User user = userRepository.findByUsername(username).orElseThrow(
		// 	() -> new IllegalArgumentException("User not found")
		// );
		Post post = Post.builder()
			.title(requestDto.getTitle())
			.contents(requestDto.getContents())
			// .user(user)
			.build();
		postRepository.save(post);
		return convertToDto(post);
	}

	public PostResponseDto updatePost(Long postId, PostRequestDto requestDto) {
		Post post = postRepository.findById(postId).orElseThrow(
			() -> new IllegalArgumentException("Post not found")
		);
		post.update(requestDto.getTitle(), requestDto.getContents());
		postRepository.save(post);
		return convertToDto(post);
	}

	public void deletePost(Long postId) {
		postRepository.deleteById(postId);
	}

	public PostResponseDto getPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(
			() -> new IllegalArgumentException("Post not found")
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
			// .userId(post.getUser().getUsername())
			// .email(post.getUser().getEamil())
			.createdAt(post.getCreatedAt())
			.updatedAt(post.getUpdatedAt())
			.build();
	}
}
