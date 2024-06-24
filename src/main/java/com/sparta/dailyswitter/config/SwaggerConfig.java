package com.sparta.dailyswitter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes("BearerAuth", new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT"))
				.addSecuritySchemes("kakao-oauth2", new SecurityScheme()
					.type(SecurityScheme.Type.OAUTH2)
					.flows(new OAuthFlows()
						.authorizationCode(new OAuthFlow()
							.authorizationUrl("https://kauth.kakao.com/oauth/authorize")
							.tokenUrl("https://kauth.kakao.com/oauth/token")
							.scopes(new Scopes()
								.addString("profile_nickname", "Profile Nickname Information")
								.addString("account_email", "Account Email Information")))))
				.addSecuritySchemes("naver-oauth2", new SecurityScheme()
					.type(SecurityScheme.Type.OAUTH2)
					.flows(new OAuthFlows()
						.authorizationCode(new OAuthFlow()
							.authorizationUrl("https://nid.naver.com/oauth2.0/authorize")
							.tokenUrl("https://nid.naver.com/oauth2.0/token")
							.scopes(new Scopes()
								.addString("name", "Name Information")
								.addString("email", "Email Information")))))
			)
			.addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
			.addSecurityItem(new SecurityRequirement().addList("kakao-oauth2"))
			.addSecurityItem(new SecurityRequirement().addList("naver-oauth2"))
			.info(new Info()
				.title("DailySwitter API")
				.description("하루 일상을 같이 공유 해봐요.")
				.version("1.0"));
	}
}
