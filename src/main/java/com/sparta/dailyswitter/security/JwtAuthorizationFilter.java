package com.sparta.dailyswitter.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.dailyswitter.common.exception.CustomException;
import com.sparta.dailyswitter.common.exception.ErrorCode;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final UserRepository userRepository;

	public JwtAuthorizationFilter(JwtUtil jwtUtil,
		UserDetailsServiceImpl userDetailsService,
		UserRepository userRepository) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String tokenValue = jwtUtil.getJwtFromHeader(request);

		log.info(tokenValue);
		if (StringUtils.hasText(tokenValue) && !request.getRequestURI().startsWith("/api/login")) {

			if (!jwtUtil.validateToken(tokenValue)) {
				log.error("Token Error");
				return;
			}

			Claims claims = jwtUtil.getUserInfoFromToken(tokenValue);
			log.info("Subject: " + claims.getSubject());
			String userId = claims.getSubject();

			// 사용자 조회
			User user = userRepository.findByUserId(userId).orElseThrow(
				() -> new CustomException(ErrorCode.USER_NOT_FOUND)
			);

			if (!user.isExist()) {
				throw new CustomException(ErrorCode.USER_NOT_FOUND);
			}

			String userRefreshToken = jwtUtil.substringToken(user.getRefreshToken());


			// 토큰이 만료된 경우
			if (jwtUtil.isTokenExpired(tokenValue)) {
				// 리프레시 토큰이 유효한 경우, 새로운 토큰 생성
				if (!jwtUtil.isRefreshTokenExpired(userRefreshToken)) {
					jwtUtil.createToken(user.getUserId());
					String newToken = jwtUtil.createToken(user.getUserId());
					log.info("새로운 토큰이 생성되었습니다: {}", newToken);
					jwtUtil.createRefreshToken(user.getUserId());
					log.info("새로운 토큰이 생성되었습니다.");
					setAuthentication(claims.getSubject());
					filterChain.doFilter(request, response);
				} else {
					// 리프레시 토큰도 만료된 경우
					throw new CustomException(ErrorCode.RELOGIN_REQUIRED);
				}
			} else {
				// 토큰이 유효한 경우
				setAuthentication(claims.getSubject());
			}
		}
		filterChain.doFilter(request, response);
	}

	// 인증 처리
	public void setAuthentication(String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}

