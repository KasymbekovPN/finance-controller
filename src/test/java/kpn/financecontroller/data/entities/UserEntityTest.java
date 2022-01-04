package kpn.financecontroller.data.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserEntityTest {

    @Test
    void shouldCheckIsSettingGetting() {
        UserEntity userEntity = new UserEntity();
        long expectedId = 123L;
        userEntity.setId(expectedId);
        assertThat(expectedId).isEqualTo(userEntity.getId());
    }

    @Test
    void shouldCheckNameSettingGetting() {
        UserEntity userEntity = new UserEntity();
        String expectedUsername = "some-user-name";
        userEntity.setName(expectedUsername);
        assertThat(expectedUsername).isEqualTo(userEntity.getName());
    }

    @Test
    void shouldCheckPasswordSettingGetting() {
        UserEntity userEntity = new UserEntity();
        String expectedPassword = "some-password";
        userEntity.setPassword(expectedPassword);
        assertThat(expectedPassword).isEqualTo(userEntity.getPassword());
    }

    @Test
    void shouldCheckRoleSettingGetting() {
        UserEntity userEntity = new UserEntity();
        Role expectedRole = Role.ROLE_ADMIN;
        userEntity.setRole(expectedRole);
        assertThat(expectedRole).isEqualTo(userEntity.getRole());
    }
}
