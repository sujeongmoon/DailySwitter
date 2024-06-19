package com.sparta.dailyswitter.domain.comment.service;

import org.springframework.stereotype.Service;

import com.sparta.dailyswitter.domain.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
}
