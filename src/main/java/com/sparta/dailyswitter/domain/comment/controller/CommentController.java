package com.sparta.dailyswitter.domain.comment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.dailyswitter.domain.comment.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

}
