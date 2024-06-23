package com.sparta.dailyswitter.domain.auth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.sparta.dailyswitter.domain.auth.userinfo.GoogleUserInfo;
import com.sparta.dailyswitter.domain.auth.userinfo.KakaoUserInfo;
import com.sparta.dailyswitter.domain.auth.userinfo.NaverUserInfo;
import com.sparta.dailyswitter.domain.auth.userinfo.OAuth2UserInfo;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.entity.UserRoleEnum;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;
import com.sparta.dailyswitter.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("getAttributes : {}", oAuth2User.getAttributes());

		OAuth2UserInfo oAuth2UserInfo = null;
		String provider = userRequest.getClientRegistration().getRegistrationId();

		if ("google".equals(provider)) {
			log.info("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if ("kakao".equals(provider)) {
			log.info("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo((Map<String, Object>) oAuth2User.getAttributes());
		} else if ("naver".equals(provider)) {
			log.info("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
		}

		if (oAuth2UserInfo == null) {
			throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
		}

		String providerId = oAuth2UserInfo.getProviderId();
		String loginId = provider + "_" + providerId;
		String nickname = oAuth2UserInfo.getName();

		Optional<User> optionalUser = userRepository.findByUserId(loginId);
		User user = optionalUser.orElseGet(() -> User.builder()
			.userId(loginId)
			.username(nickname)
			.provider(provider)
			.providerId(providerId)
			.role(UserRoleEnum.USER)
			.build());
		userRepository.save(user);

		return new UserDetailsImpl(user, oAuth2User.getAttributes());
	}
}
