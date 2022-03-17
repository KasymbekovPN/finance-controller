// TODO: 16.03.2022 del
//package kpn.financecontroller.converters;
//
//import kpn.financecontroller.data.domains.city.City;
//import kpn.financecontroller.data.domains.country.Country;
//import kpn.financecontroller.data.domains.region.Region;
//import kpn.financecontroller.data.entities.city.CityEntity;
//import kpn.financecontroller.data.entities.street.StreetEntity;
//import kpn.financecontroller.data.services.DTOService;
//import kpn.financecontroller.data.services.DTOServiceImpl;
//import kpn.financecontroller.data.services.loaders.Loader;
//import kpn.financecontroller.initialization._old.old.entities.StreetInitialEntity;
//import kpn.financecontroller.result.Result;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class StreetConverterTest {
//    private static final Long CITY_ID = 1L;
//    private static final String CITY_NAME = "city.name";
//
//    private static City city;
//
//    @BeforeAll
//    static void beforeAll() {
//        city = new City();
//        city.setId(CITY_ID);
//        city.setName(CITY_NAME);
//
//        Country country = new Country();
//        country.setId(1L);
//        country.setName("country.name");
//
//        Region region = new Region();
//        region.setId(1L);
//        region.setName("region.name");
//        region.setCountry(country);
//        city.setRegion(region);
//    }
//
//    @Test
//    void shouldCheckConversion() {
//        StreetInitialEntity initialEntity = new StreetInitialEntity();
//        long id = 123L;
//        initialEntity.setId(id);
//        String name = "name";
//        initialEntity.setName(name);
//        initialEntity.setCityId(CITY_ID);
//
//        StreetConverter converter = new StreetConverter(createRepo());
//        StreetEntity streetEntity = converter.convert(initialEntity);
//
//        assertThat(initialEntity.getId()).isEqualTo(streetEntity.getId());
//        assertThat(initialEntity.getName()).isEqualTo(streetEntity.getName());
//        assertThat(initialEntity.getCityId()).isEqualTo(streetEntity.getCityEntity().getId());
//    }
//
//    private DTOService<City, CityEntity, Long> createRepo() {
//        TestLoader loader = Mockito.mock(TestLoader.class);
//        Mockito
//                .when(loader.byId(CITY_ID))
//                .thenReturn(Result.<City>builder().success(true).value(city).build());
//        return new DTOServiceImpl<>(null, loader, null);
//    }
//
//    private abstract static class TestLoader implements Loader<City, CityEntity, Long> {}
//}