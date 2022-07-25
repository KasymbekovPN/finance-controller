// TODO: 25.07.2022 restore
//package kpn.financecontroller.data.domains.address;
//
//import kpn.financecontroller.data.domains.street.Street;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.mockito.Mockito;
//
//import java.util.ArrayDeque;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class AddressTest {
//
//    private static final String STREET_GET_INFO_ANSWER = "street getInfo answer";
//
//    @Test
//    void shouldInfoGetting() {
//
//        String addressName = "address.name";
//        Address address = new Address();
//        address.setName(addressName);
//        address.setStreet(createCity("", new ArrayDeque<>()));
//
//        String expected = addressName + ", " + STREET_GET_INFO_ANSWER;
//        assertThat(expected).isEqualTo(address.getInfo());
//    }
//
//    @ParameterizedTest
//    @CsvFileSource(resources = "shouldCheckGetting.csv")
//    void shouldCheckGetting(Long id, String name, String streetAnswer, String rawPath, String expectedResult) {
//        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
//        Address domain = new Address();
//        domain.setId(id);
//        domain.setName(name);
//        domain.setStreet(createCity(streetAnswer, path));
//
//        String result = domain.getInDeep(path);
//        assertThat(expectedResult).isEqualTo(result);
//    }
//
//    private Street createCity(String streetAnswer, ArrayDeque<String> path) {
//        Street street = Mockito.mock(Street.class);
//        Mockito
//                .when(street.getInDeep(path))
//                .thenReturn(streetAnswer);
//        Mockito
//                .when(street.getInfo())
//                .thenReturn(STREET_GET_INFO_ANSWER);
//        return street;
//    }
//}