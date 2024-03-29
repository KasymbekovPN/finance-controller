package kpn.financecontroller.security;

import kpn.financecontroller.data.domain.auxi.Role;
import kpn.financecontroller.data.domain.User;
import kpn.financecontroller.data.entity.UserEntity;
import kpn.financecontroller.data.repo.UserRepo;
import kpn.financecontroller.i18n.I18nService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UserServiceTest {

    private static final Long ID = 1L;
    private static final String USER_NAME = "name";
    private static final String PASSWORD = "password";
    private static final Role ROLE = Role.ROLE_ADMIN;
    private static final String WRONG_USER_NAME = "wrong.name";
    private static final String ERROR_TEMPLATE = "error %s";

    private static UserEntity userEntity;
    private static UserService userService;
    private static User expectedUser;

    @BeforeAll
    static void beforeAll() {
        userEntity = createUserEntity();
        userService = new UserService(createUserRepo(), createI18nService());

        expectedUser = User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .build();
    }

    @Test
    void shouldCheckUserByNameGetting() {
        User user = (User) userService.loadUserByUsername(USER_NAME);
        assertThat(expectedUser).isEqualTo(user);
    }

    @Test
    void shouldCheckLoadingUserAttemptByWrongName() {
        Throwable throwable = catchThrowable(() -> {
            userService.loadUserByUsername(WRONG_USER_NAME);
        });
        assertThat(throwable)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(String.format(ERROR_TEMPLATE, WRONG_USER_NAME));
    }

    private static I18nService createI18nService() {
        I18nService i18NService = Mockito.mock(I18nService.class);
        Mockito
                .when(i18NService.getTranslation("exception.user.notFound", WRONG_USER_NAME))
                .thenReturn(String.format(ERROR_TEMPLATE, WRONG_USER_NAME));
        return i18NService;
    }

    private static UserRepo createUserRepo() {
        UserRepo repo = Mockito.mock(UserRepo.class);
        Mockito
                .when(repo.findByUsername(USER_NAME))
                .thenReturn(List.of(userEntity));
        Mockito
                .when(repo.findByUsername(WRONG_USER_NAME))
                .thenReturn(List.of());
        return repo;
    }

    private static UserEntity createUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(ID);
        userEntity.setUsername(USER_NAME);
        userEntity.setPassword(PASSWORD);
        userEntity.setRole(ROLE);
        return userEntity;
    }
}