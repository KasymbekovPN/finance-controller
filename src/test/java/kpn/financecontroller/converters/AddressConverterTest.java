// TODO: 16.03.2022 del
//package kpn.financecontroller.converters;
//
//import kpn.financecontroller.data.domains.city.City;
//import kpn.financecontroller.data.domains.country.Country;
//import kpn.financecontroller.data.domains.region.Region;
//import kpn.financecontroller.data.domains.street.Street;
//import kpn.financecontroller.data.entities.address.AddressEntity;
//import kpn.financecontroller.data.entities.street.StreetEntity;
//import kpn.financecontroller.data.services.DTOService;
//import kpn.financecontroller.data.services.DTOServiceImpl;
//import kpn.financecontroller.data.services.loaders.Loader;
//import kpn.financecontroller.initialization._old.old.entities.BuildingInitialEntity;
//import kpn.financecontroller.result.Result;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class AddressConverterTest {
//
//    private static final Long STREET_ID = 123L;
//    private static final String STREET_NAME = "street.name";
//
//    private static Street street;
//
//    @BeforeAll
//    static void beforeAll() {
//        street = new Street();
//        street.setId(STREET_ID);
//        street.setName(STREET_NAME);
//
//        Country country = new Country();
//        country.setId(1L);
//        country.setName("country.name");
//
//        Region region = new Region();
//        region.setId(1L);
//        region.setName("region.name");
//        region.setCountry(country);
//
//        City city = new City();
//        city.setId(1L);
//        city.setName("city.name");
//        city.setRegion(region);
//
//        street.setCity(city);
//    }
//
//    @Test
//    void shouldCheckConversion() {
//        BuildingInitialEntity initialEntity = new BuildingInitialEntity();
//        long id = 123L;
//        initialEntity.setId(id);
//        String name = "name";
//        initialEntity.setName(name);
//        initialEntity.setStreetId(STREET_ID);
//
//        BuildingConverter converter = new BuildingConverter(createRepo());
//        AddressEntity addressEntity = converter.convert(initialEntity);
//
//        assertThat(initialEntity.getId()).isEqualTo(addressEntity.getId());
//        assertThat(initialEntity.getName()).isEqualTo(addressEntity.getName());
//        assertThat(initialEntity.getStreetId()).isEqualTo(addressEntity.getStreetEntity().getId());
//    }
//
//    private DTOService<Street, StreetEntity, Long> createRepo() {
//        TestLoader loader = Mockito.mock(TestLoader.class);
//        Mockito
//                .when(loader.byId(STREET_ID))
//                .thenReturn(Result.<Street>builder().success(true).value(street).build());
//        return new DTOServiceImpl<>(null, loader, null);
//    }
//
//    private abstract static class TestLoader implements Loader<Street, StreetEntity, Long> {}
//}