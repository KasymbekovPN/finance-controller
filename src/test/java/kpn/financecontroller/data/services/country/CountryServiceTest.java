package kpn.financecontroller.data.services.country;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CountryServiceTest {

    private static final String FIRST_NAME = "Russia";
    private static final String SECOND_NAME = "USA";

    private final CountryServiceOLd countryService;

    private Long expectedId;

    @Autowired
    public CountryServiceTest(CountryServiceOLd countryService) {
        this.countryService = countryService;
    }

    @BeforeEach
    void setUp() {
        expectedId = countryService.save(createCountryEntity(FIRST_NAME)).getValue().getId();
    }

    @Test
    void shouldCheckSaving() {
        Result<Country> result = countryService.save(createCountryEntity(SECOND_NAME));
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    void shouldCheckFailSavingAttempt() {
        Result<Country> result = countryService.save(createCountryEntity(FIRST_NAME));
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("service.CountryService.save.fail");
    }

    @Test
    void shouldCheckLoadAll() {
        Result<List<Country>> result = countryService.loadAll();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().size()).isEqualTo(1);
    }

    @Test
    void shouldAllDeleting() {
        countryService.deleteAll();
        Result<List<Country>> result = countryService.loadAll();
        assertThat(result.getValue()).isEmpty();
    }

    @Test
    void shouldCheckLoadingById() {
        Result<Country> result = countryService.loadById(expectedId);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().getName()).isEqualTo(FIRST_NAME);
    }

    @Test
    void shouldCheckFailLoadingAttemptById() {
        long expectedId = -1L;
        Result<Country> result = countryService.loadById(expectedId);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("service.CountryService.loadById.noOne");
        assertThat(result.getArgs()).isEqualTo(List.of(expectedId).toArray());
    }

    @Test
    void shouldCheckDeleting() {
        countryService.deleteById(expectedId);
        Result<Country> result = countryService.loadById(expectedId);
        assertThat(result.getSuccess()).isFalse();
    }

    @Test
    void shouldCheckSearchingByFilter() {
        String filter = "R";
        Result<List<Country>> result =  countryService.search(filter);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().size()).isEqualTo(1);
    }

    @AfterEach
    void tearDown() {
        countryService.deleteAll();
    }

    private CountryEntity createCountryEntity(String name) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName(name);
        return countryEntity;
    }
}
