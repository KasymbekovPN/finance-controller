package kpn.financecontroller.data.repos.region;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.repos.country.CountryRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RegionRepoTest {

    private final CountryRepo countryRepo;
    private final RegionRepo regionRepo;
    private CountryEntity countryEntity;
    private Long expectedRegionId;

    @Autowired
    public RegionRepoTest(CountryRepo countryRepo, RegionRepo regionRepo) {
        this.countryRepo = countryRepo;
        this.regionRepo = regionRepo;
    }

    @BeforeEach
    void setUp() {
        countryEntity = new CountryEntity();
        countryEntity.setName("Russia");
        Long countryId = countryRepo.save(countryEntity).getId();

        expectedRegionId = regionRepo.save(createRegion("Prokopjevsk", countryEntity)).getId();
    }

    @Test
    void shouldCheckSaving() {
        RegionEntity entity = regionRepo.save(createRegion("Moscow", countryEntity));
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNotNull();
    }

    @Test
    void shouldCheckFindingById() {
        Optional<RegionEntity> maybeEntity = regionRepo.findById(expectedRegionId);
        assertThat(maybeEntity).isPresent();
    }

    @Test
    void shouldCheckDeleting() {
        regionRepo.deleteById(expectedRegionId);
        Optional<RegionEntity> maybeEntity = regionRepo.findById(expectedRegionId);
        assertThat(maybeEntity).isEmpty();
    }

    @Test
    void shouldCheckAllFinding() {
        List<RegionEntity> entities = regionRepo.findAll();
        assertThat(entities.size()).isEqualTo(1);
    }

    @AfterEach
    void tearDown() {
        regionRepo.deleteAll();
        countryRepo.deleteAll();
    }

    private RegionEntity createRegion(String name, CountryEntity countryEntity) {
        RegionEntity entity = new RegionEntity();
        entity.setName(name);
        entity.setCountryEntity(countryEntity);
        return entity;
    }
}