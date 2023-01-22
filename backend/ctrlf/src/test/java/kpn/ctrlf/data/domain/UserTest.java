package kpn.ctrlf.data.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

	private static final String USER_NAME = "username";
	private static final String PASSWORD = "password";

	@Test
	void shouldCheckAuthoritiesGetter() {
		Collection<? extends GrantedAuthority> authorities = createUser().getAuthorities();
		assertThat(authorities.size()).isZero();
	}

	@Test
	void shouldCheckPasswordGetter() {
		assertThat(createUser().getPassword()).isEqualTo(PASSWORD);
	}

	@Test
	void shouldCheckUsernameGetter() {
		assertThat(createUser().getUsername()).isEqualTo(USER_NAME);
	}

	@Test
	void shouldCheckAccountExpirationChecking() {
		assertThat(createUser().isAccountNonExpired()).isTrue();
	}

	@Test
	void shouldCheckAccountBlockingChecking() {
		assertThat(createUser().isAccountNonLocked()).isTrue();
	}

	@Test
	void shouldCheckCredentialsExpirationChecking() {
		assertThat(createUser().isCredentialsNonExpired()).isTrue();
	}

	@Test
	void shouldCheckIsEnabledChecking() {
		assertThat(createUser().isEnabled()).isTrue();
	}

	private User createUser(){
		return new User(USER_NAME, PASSWORD);
	}
}
