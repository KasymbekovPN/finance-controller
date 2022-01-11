package kpn.financecontroller.data.services.deleters.country;

import kpn.financecontroller.data.repos.country.CountryRepo;
import kpn.financecontroller.data.services.deleters.country.CountryDeleter;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class CountryDeleterTest {

    private static CountryDeleter deleter;

    @BeforeAll
    static void beforeAll() {
        deleter = new CountryDeleter(createRepoMock());
    }

    @Test
    void shouldCheckById() {
        Result<Void> result = deleter.byId(1L);
        assertThat(result.getSuccess()).isTrue();
    }

    @Test
    void shouldCheckBy() {
        Random random = new Random();
        String attribute = String.format("attribute_%s", random.nextInt());
        String value = String.format("value_%s", random.nextInt());
        Result<Void> result = deleter.by(attribute, value);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("deleter.by.attribute.disallowed");
        assertThat(result.getArgs()).isEqualTo(List.of("CountryDeleter", attribute, value).toArray());
    }

    @Test
    void shouldCheckAll() {
        Result<Void> result = deleter.all();
        assertThat(result.getSuccess()).isTrue();
    }

    private static CountryRepo createRepoMock() {
        return Mockito.mock(CountryRepo.class);
    }
}