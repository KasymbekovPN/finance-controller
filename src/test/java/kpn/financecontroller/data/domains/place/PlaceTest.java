package kpn.financecontroller.data.domains.place;

import kpn.financecontroller.data.domains.address.Address;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting.csv")
    void shouldCheckGetting(Long id, String name, boolean online, String addressAnswer, String rawPath, String expectedResult) {
        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
        Place domain = new Place();
        domain.setId(id);
        domain.setName(name);
        domain.setOnline(online);
        domain.setAddress(createAddress(addressAnswer));

        String result = domain.get(path);
        assertThat(expectedResult).isEqualTo(result);
    }

    private Address createAddress(String streetAnswer) {
        Address address = Mockito.mock(Address.class);
        Mockito
                .when(address.getInfo())
                .thenReturn(streetAnswer);
        return address;
    }
}