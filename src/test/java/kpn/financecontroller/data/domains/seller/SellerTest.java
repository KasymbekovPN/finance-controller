package kpn.financecontroller.data.domains.seller;

import kpn.financecontroller.data.domains.address.Address;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SellerTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting.csv")
    void shouldCheckGetting(Long id,
                            String name,
                            String url,
                            String description,
                            String addressAnswer,
                            String rawPath,
                            String expectedResult) {
        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
        Seller domain = new Seller();
        domain.setId(id);
        domain.setName(name);
        domain.setUrl(url);
        domain.setDescription(description);
        domain.setAddress(createAddress(addressAnswer));

        String result = domain.getInDeep(path);
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