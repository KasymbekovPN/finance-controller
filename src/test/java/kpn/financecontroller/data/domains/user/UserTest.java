package kpn.financecontroller.data.domains.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private static final String USER_NAME = "user name";
    private static final String PASSWORD = "some password";
    private static final Role ROLE = Role.ROLE_ADMIN;

    @Test
    void shouldCheckUsername() {
        User user = User.builder().username(USER_NAME).build();
        assertThat(USER_NAME).isEqualTo(user.getUsername());
    }

    @Test
    void shouldCheckPassword() {
        User user = User.builder().password(PASSWORD).build();
        assertThat(PASSWORD).isEqualTo(user.getPassword());
    }

    @Test
    void shouldCheckAuthorities() {
        User user = User.builder().role(ROLE).build();
        assertThat(List.of(new SimpleGrantedAuthority(ROLE.name()))).isEqualTo(user.getAuthorities());
    }

    @Test
    void shouldCheckAccountNonExpiration() {
        assertThat(User.builder().build().isAccountNonExpired()).isTrue();
    }

    @Test
    void shouldCheckAccountNonLocked() {
        assertThat(User.builder().build().isAccountNonLocked()).isTrue();
    }

    @Test
    void shouldCheckCredentialsNonExpired() {
        assertThat(User.builder().build().isCredentialsNonExpired()).isTrue();
    }

    @Test
    void shouldCheckIsEnabled() {
        assertThat(User.builder().build().isEnabled()).isTrue();
    }
}