package com.sparta.dailyswitter.commentlike;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.sparta.dailyswitter.TestConfig;
import com.sparta.dailyswitter.TestUtil;
import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.comment.repository.CommentRepository;
import com.sparta.dailyswitter.domain.like.commentlike.entity.CommentLike;
import com.sparta.dailyswitter.domain.like.commentlike.repository.CommentLikeRepository;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.repository.PostRepository;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class commentLikeRepositoryTest {

	@Autowired
	private CommentLikeRepository commentLikeRepository;

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@Test
	@DisplayName("내가 좋아하는 댓글 목록 리스트 테스트")
	void getCommentTest() {

		//given
		User user1 = TestUtil.settingUser();
		User user2 = TestUtil.settingUser2();
		User user3 = TestUtil.settingUser3();
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);

		Post post1 = TestUtil.settingPost(user1);
		Post post2 = TestUtil.settingPost(user1);
		postRepository.save(post1);
		postRepository.save(post2);

		Comment comment1 = TestUtil.settingComment(user1, post1);
		Comment comment2 = TestUtil.settingComment(user1, post1);
		Comment comment3 = TestUtil.settingComment(user1, post2);
		commentRepository.save(comment1);
		commentRepository.save(comment2);
		commentRepository.save(comment3);

		CommentLike commentLike1 = TestUtil.settingCommentLike(user2, comment1);
		CommentLike commentLike2 = TestUtil.settingCommentLike(user2, comment3);
		CommentLike commentLike3  = TestUtil.settingCommentLike(user3, comment3);
		commentLikeRepository.save(commentLike1);
		commentLikeRepository.save(commentLike2);
		commentLikeRepository.save(commentLike3);

		//when
		List<Comment> commentLikeList = commentLikeRepository.getCommentLikeCommentId(user2);

		//then
		assertThat(commentLikeList.size()).isEqualTo(2);

	}

}
