package com.sparta.dailyswitter.comment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sparta.dailyswitter.TestConfig;
import com.sparta.dailyswitter.domain.comment.dto.CommentRequestDto;
import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.comment.repository.CommentRepository;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.entity.UserRoleEnum;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class commentRepositoryTest {

	@Autowired
	private CommentRepository commentRepository;

	private User settingUser() {
		User user = User.builder()
			.userId("user1")
			.username("user1")
			.password("Qwerasdf1!")
			.email("11@11.com")
			.intro("자기소개")
			.role(UserRoleEnum.USER)
			.kakaoId("user1")
			.naverId("user1")
			.build();
		return user;
	}

	private Post settingPost(User user) {
		Post post = Post.builder()
			.title("제목입니다")
			.contents("컨텐츠")
			.user(user)
			.build();
		return post;
	}

	private Comment settingComment(User user, Post post) {
		CommentRequestDto requestDto = Mockito.mock(CommentRequestDto.class);
		given(requestDto.getContent()).willReturn("댓글내용11");

		Comment comment = Comment.builder()
			.user(user)
			.post(post)
			.requestDto(requestDto)
			.build();
		commentRepository.save(comment);

		return comment;
	}

	private Comment settingAnotherComment(User user, Post post) {
		CommentRequestDto requestDto = Mockito.mock(CommentRequestDto.class);
		given(requestDto.getContent()).willReturn("댓글내용22");

		Comment comment = Comment.builder()
			.user(user)
			.post(post)
			.requestDto(requestDto)
			.build();
		commentRepository.save(comment);

		return comment;
	}

	@Test
	@DisplayName("comment 페이지네이션 조회 테스트")
	void getCommentTest() {

		// given
		User user = settingUser();
		Post post = settingPost(user);
		Comment comment1 = settingComment(user, post);
		Comment comment2 = settingAnotherComment(user, post);

		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		Pageable pageable = PageRequest.of(0, 5, sort);

		// when
		Page<Comment> commentList = commentRepository.getComment(post, pageable);

		// then
		assertThat(commentList.getTotalElements()).isEqualTo(2);
	}

}
