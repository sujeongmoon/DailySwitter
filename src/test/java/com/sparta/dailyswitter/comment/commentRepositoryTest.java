package com.sparta.dailyswitter.comment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.dailyswitter.TestConfig;
import com.sparta.dailyswitter.TestUtil;
import com.sparta.dailyswitter.domain.comment.entity.Comment;
import com.sparta.dailyswitter.domain.comment.repository.CommentRepository;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.repository.PostRepository;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class commentRepositoryTest {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@Test
	@DisplayName("comment 페이지네이션 조회 테스트")
	void getCommentTest() {

		// given
		User user = TestUtil.settingUser();
		userRepository.save(user);
		Post post = TestUtil.settingPost(user);
		postRepository.save(post);
		Comment comment1 = TestUtil.settingComment(user, post);
		Comment comment2 = TestUtil.settingComment(user, post);
		commentRepository.save(comment1);
		commentRepository.save(comment2);

		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		Pageable pageable = PageRequest.of(0, 5, sort);

		// when
		Page<Comment> commentList = commentRepository.getComment(post, pageable);

		// then
		assertThat(commentList.getTotalElements()).isEqualTo(2);
	}

}
