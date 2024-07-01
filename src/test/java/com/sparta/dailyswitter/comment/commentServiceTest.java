package com.sparta.dailyswitter.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class commentServiceTest {


	@Test
	@Transactional(propagation = Propagation.NEVER)
	@DisplayName("페이지네이션 조회")
	void getCommentTest() {
		// given

		// when


		// then

	}


}
