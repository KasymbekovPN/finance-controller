// TODO: 16.03.2022 del
//package kpn.financecontroller.converters;
//
//import kpn.financecontroller.data.domains.country.Country;
//import kpn.financecontroller.data.domains.region.Region;
//import kpn.financecontroller.data.entities.city.CityEntity;
//import kpn.financecontroller.data.entities.region.RegionEntity;
//import kpn.financecontroller.data.services.DTOService;
//import kpn.financecontroller.data.services.DTOServiceImpl;
//import kpn.financecontroller.data.services.loaders.Loader;
//import kpn.financecontroller.initialization._old.old.entities.CityInitialEntity;
//import kpn.financecontroller.result.Result;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class CityConverterTest {
//
//    private static final Long REGION_ID = 1L;
//    private static final String REGION_NAME = "region.name";
//
//    private static Region region;
//
//
//    @BeforeAll
//    static void beforeAll() {
//        region = new Region();
//        region.setId(REGION_ID);
//        region.setName(REGION_NAME);
//
//        Country country = new Country();
//        country.setId(1L);
//        country.setName("county.name");
//        region.setCountry(country);
//    }
//
//    @Test
//    void shouldCheckConversion() {
//        CityInitialEntity initialEntity = new CityInitialEntity();
//        long id = 123L;
//        initialEntity.setId(id);
//        String name = "name";
//        initialEntity.setName(name);
//        initialEntity.setRegionId(REGION_ID);
//
//        CityConverter converter = new CityConverter(createRepo());
//        CityEntity cityEntity = converter.convert(initialEntity);
//
//        assertThat(initialEntity.getId()).isEqualTo(cityEntity.getId());
//        assertThat(initialEntity.getName()).isEqualTo(cityEntity.getName());
//        assertThat(initialEntity.getRegionId()).isEqualTo(cityEntity.getRegionEntity().getId());
//    }
//
//    private static DTOService<Region, RegionEntity, Long> createRepo() {
//        TestLoader loader = Mockito.mock(TestLoader.class);
//        Mockito
//                .when(loader.byId(REGION_ID))
//                .thenReturn(Result.<Region>builder().success(true).value(region).build());
//        return new DTOServiceImpl<>(null, loader, null);
//    }
//
//    private abstract static class TestLoader implements Loader<Region, RegionEntity, Long>{}
//}