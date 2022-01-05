package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.domain.user.Role;
import kpn.financecontroller.data.entities.user.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UserRepoTest {

    private static final String OLD_USERNAME = "old user";
    private static final String OLD_PASSWORD = "old pass";
    private static final Role OLD_ROLE = Role.ROLE_ADMIN;

    private static final String NEW_USERNAME = "new user";
    private static final String NEW_PASSWORD = "new pass";
    private static final Role NEW_ROLE = Role.ROLE_ADMIN;

    private final UserRepo userRepo;
    private Long expectedId;

    @Autowired
    public UserRepoTest(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @BeforeEach
    void setUp() {
        UserEntity userEntity = createUserEntity(OLD_USERNAME, OLD_PASSWORD, OLD_ROLE);
        expectedId = userRepo.save(userEntity).getId();
    }

    @Test
    void shouldCheckSaving() {
        UserEntity userEntity = createUserEntity(NEW_USERNAME, NEW_PASSWORD, NEW_ROLE);
        UserEntity savedUserEntity = userRepo.save(userEntity);
        assertThat(savedUserEntity).isNotNull();
        assertThat(savedUserEntity.getId()).isNotNull();
    }

    @Test
    void shouldCheckFinding() {
        Optional<UserEntity> maybeUserEntity = userRepo.findById(expectedId);
        assertThat(maybeUserEntity).isPresent();
        UserEntity userEntity = maybeUserEntity.get();
        assertThat(OLD_USERNAME).isEqualTo(userEntity.getUsername());
        assertThat(OLD_PASSWORD).isEqualTo(userEntity.getPassword());
        assertThat(OLD_ROLE).isEqualTo(userEntity.getRole());
    }

    @Test
    void shouldCheckFindingByUserName() {
        List<UserEntity> userEntities = userRepo.findByUsername(OLD_USERNAME);
        assertThat(1).isEqualTo(userEntities.size());
        UserEntity userEntity = userEntities.get(0);
        assertThat(OLD_USERNAME).isEqualTo(userEntity.getUsername());
        assertThat(OLD_PASSWORD).isEqualTo(userEntity.getPassword());
        assertThat(OLD_ROLE).isEqualTo(userEntity.getRole());
    }

    @Test
    void shouldCheckDeleting() {
        userRepo.deleteById(expectedId);
        Optional<UserEntity> maybeUserEntity = userRepo.findById(expectedId);
        assertThat(maybeUserEntity).isEmpty();
    }

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }

    private UserEntity createUserEntity(String username, String password, Role role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setRole(role);

        return userEntity;
    }
}