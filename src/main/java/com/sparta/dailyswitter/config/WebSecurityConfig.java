package com.sparta.dailyswitter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sparta.dailyswitter.domain.auth.service.PrincipalOauth2UserService;
import com.sparta.dailyswitter.domain.user.entity.UserRoleEnum;
import com.sparta.dailyswitter.domain.user.repository.UserRepository;
import com.sparta.dailyswitter.security.JwtAuthenticationFilter;
import com.sparta.dailyswitter.security.JwtAuthorizationFilter;
import com.sparta.dailyswitter.security.JwtUtil;
import com.sparta.dailyswitter.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final PrincipalOauth2UserService principalOauth2UserService;
	private final UserRepository userRepository;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		AuthenticationManager authenticationManager = authenticationManager(authenticationConfiguration);

		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
					"/resources/**", "/static/**", "/css/**", "/js/**", "/images/**"
				).permitAll()
				.requestMatchers(
					"/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
					"/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**",
					"/webjars/**", "/swagger-ui.html", "/api/auth/**"
				).permitAll()
				.requestMatchers("/security-login/info").authenticated()
				.requestMatchers("/security-login/admin/**").hasAuthority(UserRoleEnum.ADMIN.name())
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/security-login/login")
				.usernameParameter("loginId")
				.passwordParameter("password")
				.defaultSuccessUrl("/security-login")
				.failureUrl("/security-login/login")
			)
			.logout(logout -> logout
				.logoutUrl("/security-login/logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
			)
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/security-login/login")
				.defaultSuccessUrl("/security-login")
				.userInfoEndpoint()
				.userService(principalOauth2UserService)
			)
			.addFilterBefore(new JwtAuthorizationFilter(jwtUtil, userDetailsService, userRepository), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, authenticationManager, userRepository), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.addExposedHeader("Authorization");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(
		ClientRegistrationRepository clientRegistrationRepository,
		OAuth2AuthorizedClientRepository authorizedClientRepository) {

		DefaultOAuth2AuthorizedClientManager authorizedClientManager =
			new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);

		return authorizedClientManager;
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(googleClientRegistration());
	}

	private ClientRegistration googleClientRegistration() {
		return ClientRegistration.withRegistrationId("google")
			.clientId("your-client-id")
			.clientSecret("your-client-secret")
			.scope("openid", "profile", "email")
			.authorizationUri("https://accounts.google.com/o/oauth2/auth")
			.tokenUri("https://oauth2.googleapis.com/token")
			.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
			.userNameAttributeName("sub")
			.clientName("Google")
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)  // 설정 추가
			.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")  // 설정 추가
			.build();
	}
}
