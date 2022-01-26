package kpn.financecontroller.security;

import kpn.financecontroller.data.domains.user.Role;
import kpn.financecontroller.data.entities.user.UserEntity;
import kpn.financecontroller.data.repos.user.UserRepo;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceContextTest {

    private static final String WRONG_USER_NAME = "wrong user name";

    private static final String USER_NAME = "user name";
    private static final String PASSWORD = "password";
    private static final Role ROLE = Role.ROLE_ADMIN;

    private final UserService userService;
    private final UserRepo userRepo;
    private final StandardPBEStringEncryptor encryptor;

    @Autowired
    public UserServiceContextTest(UserService userService, UserRepo userRepo, StandardPBEStringEncryptor encryptor) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.encryptor = encryptor;
    }

    @BeforeEach
    void setUp() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(USER_NAME);
        userEntity.setPassword(encryptor.encrypt(PASSWORD));
        userEntity.setRole(ROLE);

        userRepo.save(userEntity);
    }

    @Test
    void shouldCheckUserLoading() {
        UserDetails user = userService.loadUserByUsername(USER_NAME);
        assertThat(user).isNotNull();
        assertThat(USER_NAME).isEqualTo(user.getUsername());
        assertThat(PASSWORD).isEqualTo(user.getPassword());
        assertThat(List.of(new SimpleGrantedAuthority(ROLE.name()))).isEqualTo(user.getAuthorities());
    }

    @Test
    void shouldCheckWrongUserLoading() {
        Throwable throwable = catchThrowable(() -> {
            userService.loadUserByUsername(WRONG_USER_NAME);
        });
        assertThat(throwable).isInstanceOf(UsernameNotFoundException.class);
    }

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }
}