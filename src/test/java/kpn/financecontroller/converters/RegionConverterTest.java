// TODO: 16.03.2022 del
//package kpn.financecontroller.converters;
//
//import kpn.financecontroller.data.domains.country.Country;
//import kpn.financecontroller.data.entities.country.CountryEntity;
//import kpn.financecontroller.data.entities.region.RegionEntity;
//import kpn.financecontroller.data.services.DTOService;
//import kpn.financecontroller.data.services.DTOServiceImpl;
//import kpn.financecontroller.data.services.loaders.Loader;
//import kpn.financecontroller.initialization._old.old.entities.RegionInitialEntity;
//import kpn.financecontroller.result.Result;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class RegionConverterTest {
//
//    private static final Long COUNTRY_ID = 1L;
//    private static final String COUNTRY_NAME = "country.name";
//
//    private static Country country;
//
//    @BeforeAll
//    static void beforeAll() {
//        country = new Country();
//        country.setId(COUNTRY_ID);
//        country.setName(COUNTRY_NAME);
//    }
//
//    @Test
//    void shouldCheckConversion() {
//        RegionInitialEntity initialEntity = new RegionInitialEntity();
//        long id = 123L;
//        initialEntity.setId(id);
//        String name = "name";
//        initialEntity.setName(name);
//        initialEntity.setCountryId(COUNTRY_ID);
//
//        RegionConverter converter = new RegionConverter(createRepo());
//        RegionEntity regionEntity = converter.convert(initialEntity);
//
//        assertThat(initialEntity.getId()).isEqualTo(regionEntity.getId());
//        assertThat(initialEntity.getName()).isEqualTo(regionEntity.getName());
//        assertThat(initialEntity.getCountryId()).isEqualTo(regionEntity.getCountryEntity().getId());
//    }
//
//    private static DTOService<Country, CountryEntity, Long> createRepo() {
//        TestLoader loader = Mockito.mock(TestLoader.class);
//        Mockito
//                .when(loader.byId(COUNTRY_ID))
//                .thenReturn(Result.<Country>builder().success(true).value(country).build());
//        return new DTOServiceImpl<>(null, loader, null);
//    }
//
//    private abstract static class TestLoader implements Loader<Country, CountryEntity, Long>{}
//}