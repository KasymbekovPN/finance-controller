package kpn.financecontroller.data.repo.country;

import kpn.financecontroller.data.entities.country.CountryEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@ActiveProfiles("test")
public class CountryRepoTest {

    private static final String FIRST_NAME = "Russia";
    private static final String SECOND_NAME = "USA";

    private static final String[] OTHER_NAMES = {
            "Canada",
            "Ukraine",
            "UK"
    };

    private final CountryRepo countryRepo;

    private Long expectedId;

    @Autowired
    public CountryRepoTest(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    @BeforeEach
    void setUp() {
        expectedId = countryRepo.save(createCountryEntity(FIRST_NAME)).getId();
    }

    @Test
    void shouldCheckSaving() {
        CountryEntity savedEntity = countryRepo.save(createCountryEntity(SECOND_NAME));
        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isNotNull();
    }

    @Test
    void shouldCheckFindingById() {
        Optional<CountryEntity> maybeEntity = countryRepo.findById(expectedId);
        assertThat(maybeEntity).isPresent();
    }

    @Test
    void shouldCheckFindingByName() {
        List<CountryEntity> entities = countryRepo.findByName(FIRST_NAME);
        assertThat(entities.size()).isEqualTo(1);
        CountryEntity entity = entities.get(0);
        assertThat(FIRST_NAME).isEqualTo(entity.getName());
    }

    @Test
    void shouldCheckDuplicateNameInsertionAttempt() {
        Throwable throwable = catchThrowable(() -> {
            countryRepo.save(createCountryEntity(FIRST_NAME));
        });
        assertThat(throwable).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldCheckDeleting() {
        countryRepo.deleteById(expectedId);
        Optional<CountryEntity> maybeEntity = countryRepo.findById(expectedId);
        assertThat(maybeEntity).isEmpty();
    }

    @Test
    void shouldCheckAllFinding() {
        saveOtherNames();
        List<CountryEntity> entities = countryRepo.findAll();
        assertThat(entities.size()).isEqualTo(1 + OTHER_NAMES.length);
    }

    @Test
    void shouldCheckSearching() {
        saveOtherNames();
        String filter = "U";
        List<CountryEntity> entities = countryRepo.search(filter);
        assertThat(entities.size()).isEqualTo(2);
    }

    @AfterEach
    void tearDown() {
        countryRepo.deleteAll();
    }

    private void saveOtherNames() {
        Arrays.stream(OTHER_NAMES).map(name -> {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setName(name);
            return countryEntity;
        }).forEach(countryRepo::save);
    }

    private CountryEntity createCountryEntity(String name) {
        CountryEntity currencyEntity = new CountryEntity();
        currencyEntity.setName(name);
        return currencyEntity;
    }
}
