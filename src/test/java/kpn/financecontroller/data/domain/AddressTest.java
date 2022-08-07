package kpn.financecontroller.data.domain;

import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.data.domain.Street;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest {

    private static final String STREET_GET_INFO_ANSWER = "street getInfo answer";

    @Test
    void shouldInfoGetting() {

        String addressName = "address.name";
        Address address = new Address();
        address.setName(addressName);
        address.setStreet(createCity());

        String expected = addressName + ", " + STREET_GET_INFO_ANSWER;
        assertThat(expected).isEqualTo(address.getInfo());
    }

    private Street createCity() {
        Street street = Mockito.mock(Street.class);
        Mockito
                .when(street.getInfo())
                .thenReturn(STREET_GET_INFO_ANSWER);
        return street;
    }
}