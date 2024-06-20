package com.sparta.dailyswitter.domain.post.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.post.dto.PostRequestDto;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

	private PostService postService;

	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
		PostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUsername());
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
		PostResponseDto responseDto = postService.updatePost(id, requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPost(@PathVariable Long id) {
		PostResponseDto responseDto = postService.getPost(id);
		return ResponseEntity.ok(responseDto);
	}
}
