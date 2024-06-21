package com.sparta.dailyswitter.domain.post.controller;

import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.post.dto.PostRequestDto;
import com.sparta.dailyswitter.domain.post.dto.PostResponseDto;
import com.sparta.dailyswitter.domain.post.service.PostService;
import com.sparta.dailyswitter.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
		}
		PostResponseDto responseDto = postService.createPost(requestDto, userDetails.getUsername());
		return ResponseEntity.ok(responseDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
		}
		PostResponseDto responseDto = postService.updatePost(id, requestDto, userDetails.getUsername());
		return ResponseEntity.ok(responseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
		if (userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
		}
		postService.deletePost(id, userDetails.getUsername());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPost(@PathVariable Long id) {
		PostResponseDto responseDto = postService.getPost(id);
		return ResponseEntity.ok(responseDto);
	}

	@GetMapping
	public Page<PostResponseDto> getAllPosts(@RequestParam(defaultValue = "0") int page) {
		Pageable pageable = PageRequest.of(page, 5);
		return postService.getAllPosts(pageable);
	}
}
