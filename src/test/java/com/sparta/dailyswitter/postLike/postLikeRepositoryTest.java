package com.sparta.dailyswitter.postLike;

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
import com.sparta.dailyswitter.domain.like.postlike.entity.PostLike;
import com.sparta.dailyswitter.domain.like.postlike.repository.PostLikeRepository;
import com.sparta.dailyswitter.domain.post.entity.Post;
import com.sparta.dailyswitter.domain.post.repository.PostRepository;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class postLikeRepositoryTest {

	@Autowired
	private PostLikeRepository postLikeRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@Test
	@DisplayName("내가 좋아하는 게시글 목록 리스트 테스트")
	void getPostTest() {

		//given
		User user1 = TestUtil.settingUser();
		User user2 = TestUtil.settingUser2();
		User user3 = TestUtil.settingUser3();
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);

		Post post1 = TestUtil.settingPost(user1);
		Post post2 = TestUtil.settingPost(user1);
		Post post3 = TestUtil.settingPost(user1);
		postRepository.save(post1);
		postRepository.save(post2);
		postRepository.save(post3);

		PostLike postLike1 = TestUtil.settingPostLike(user2, post1);
		PostLike postLike2 = TestUtil.settingPostLike(user2, post2);
		PostLike postLike3 = TestUtil.settingPostLike(user3, post1);
		postLikeRepository.save(postLike1);
		postLikeRepository.save(postLike2);
		postLikeRepository.save(postLike3);

		//when
		List<Post> postLikeList = postLikeRepository.getPostLikePostId(user2);

		//then
		assertThat(postLikeList.size()).isEqualTo(2);
	}
}
