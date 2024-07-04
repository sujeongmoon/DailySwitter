package com.sparta.dailyswitter;

import static org.mockito.BDDMockito.given;

import org.mockito.Mockito;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.dailyswitter.domain.comment.dto.CommentRequestDto;
import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.like.commentlike.entity.CommentLike;
import com.sparta.dailyswitter.domain.like.commentlike.entity.CommentLikeId;
import com.sparta.dailyswitter.domain.like.postlike.entity.PostLike;
import com.sparta.dailyswitter.domain.like.postlike.entity.PostLikeId;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.entity.UserRoleEnum;

@Transactional
public class TestUtil {

	public static User settingUser() {
		User user = User.builder()
			.userId("testuser1")
			.username("testuser1")
			.password("Qwerasdf1!")
			.email("11@11.com")
			.intro("자기소개")
			.role(UserRoleEnum.USER)
			.kakaoId("user1")
			.naverId("user1")
			.build();

		return user;
	}

	public static User settingUser2() {
		User user = User.builder()
			.userId("testuser2")
			.username("testuser2")
			.password("Qwerasdf1!")
			.email("11@11.com")
			.intro("자기소개")
			.role(UserRoleEnum.USER)
			.kakaoId("user2")
			.naverId("user2")
			.build();

		return user;
	}

	public static User settingUser3() {
		User user = User.builder()
			.userId("testuser3")
			.username("testuser3")
			.password("Qwerasdf1!")
			.email("11@11.com")
			.intro("자기소개")
			.role(UserRoleEnum.USER)
			.kakaoId("user3")
			.naverId("user3")
			.build();

		return user;
	}

	public static Post settingPost(User user) {
		Post post = Post.builder()
			.title("test제목입니다")
			.contents("test컨텐츠")
			.user(user)
			.build();
		return post;
	}

	public static Comment settingComment(User user, Post post) {
		CommentRequestDto requestDto = Mockito.mock(CommentRequestDto.class);
		given(requestDto.getContent()).willReturn("test댓글내용11");

		Comment comment = Comment.builder()
			.user(user)
			.post(post)
			.requestDto(requestDto)
			.build();

		return comment;
	}

	public static CommentLike settingCommentLike(User user, Comment comment) {
		CommentLikeId commentLikeId = CommentLikeId.builder()
			.user(user)
			.comment(comment)
			.build();

		CommentLike commentLike = CommentLike.builder()
			.id(commentLikeId)
			.build();

		return commentLike;
	}

	public static PostLike settingPostLike(User user, Post post) {
		PostLikeId postLikeId = PostLikeId.builder()
			.user(user)
			.post(post)
			.build();

		PostLike postLike = PostLike.builder()
			.id(postLikeId)
			.build();

		return postLike;
	}
}
