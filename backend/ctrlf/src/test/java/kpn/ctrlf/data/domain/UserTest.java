package kpn.ctrlf.data.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

	private static final String USER_NAME = "username";
	private static final String PASSWORD = "password";

	@Test
	void shouldCheckPasswordGetter() {
		assertThat(createUser().getPassword()).isEqualTo(PASSWORD);
	}

	@Test
	void shouldCheckUsernameGetter() {
		assertThat(createUser().getUsername()).isEqualTo(USER_NAME);
	}

	private User createUser(){
		return new User(USER_NAME, PASSWORD);
	}
}
