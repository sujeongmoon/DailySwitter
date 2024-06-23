package com.sparta.dailyswitter.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.sparta.dailyswitter.domain.auth.userinfo.OAuth2UserInfo;
import com.sparta.dailyswitter.domain.user.entity.User;
import com.sparta.dailyswitter.domain.user.entity.UserRoleEnum;

public class UserDetailsImpl implements UserDetails, OAuth2User, OAuth2UserInfo {

	private final User user;
	private Map<String, Object> attributes;

	public UserDetailsImpl(User user) {
		this.user = user;
	}

	public UserDetailsImpl(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	public User getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		UserRoleEnum role = user.getRole();
		return Collections.singleton(new SimpleGrantedAuthority(role.getAuthority()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getName() {
		return user.getUsername();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getProviderId() {
		return user.getProviderId();
	}

	@Override
	public String getProvider() {
		return user.getProvider();
	}

	@Override
	public String getEmail() {
		return user.getEmail();
	}
}
