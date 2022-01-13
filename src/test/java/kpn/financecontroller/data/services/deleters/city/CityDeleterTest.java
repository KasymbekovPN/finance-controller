package kpn.financecontroller.data.services.deleters.city;

import kpn.financecontroller.data.repos.city.CityRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class CityDeleterTest {

    private static CityDeleter deleter;

    @BeforeAll
    static void beforeAll() {
        deleter = new CityDeleter(createRepoMock());
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
        assertThat(result.getArgs()).isEqualTo(List.of("CityDeleter", attribute, value).toArray());
    }

    @Test
    void shouldCheckAll() {
        Result<Void> result = deleter.all();
        assertThat(result.getSuccess()).isTrue();
    }

    private static CityRepo createRepoMock(){
        return Mockito.mock(CityRepo.class);
    }
}