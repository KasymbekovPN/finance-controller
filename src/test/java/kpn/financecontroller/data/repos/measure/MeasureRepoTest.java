package kpn.financecontroller.data.repos.measure;

import kpn.financecontroller.data.entities.measure.MeasureEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@ActiveProfiles("test")
public class MeasureRepoTest {

    private static final String FIRST_CODE = "kg";
    private static final String SECOND_CODE = "gr";
    private static final String[] OTHER_CODES = {
            "pack",
            "item0",
            "item1",
            "item2"
    };

    private final MeasureRepo measureRepo;

    private Long expectedId;

    @Autowired
    public MeasureRepoTest(MeasureRepo measureRepo) {
        this.measureRepo = measureRepo;
    }

    @BeforeEach
    void setUp() {
        expectedId = measureRepo.save(createMeasureEntity(FIRST_CODE)).getId();
    }

    @Test
    void shouldCheckSaving() {
        MeasureEntity savedEntity = measureRepo.save(createMeasureEntity(SECOND_CODE));
        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getId()).isNotNull();
    }

    @Test
    void shouldCheckFindingById() {
        Optional<MeasureEntity> maybeEntity = measureRepo.findById(expectedId);
        assertThat(maybeEntity).isPresent();
        assertThat(FIRST_CODE).isEqualTo(maybeEntity.get().getCode());
    }

    @Test
    void shouldCheckFindingByCode() {
        List<MeasureEntity> measureEntities = measureRepo.findByCode(FIRST_CODE);
        assertThat(measureEntities.size()).isEqualTo(1);
    }

    @Test
    void shouldCheckDuplicateCodeInsertionAttempt() {
        Throwable throwable = catchThrowable(() -> {
            measureRepo.save(createMeasureEntity(FIRST_CODE));
        });
        assertThat(throwable).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldCheckDeleting() {
        measureRepo.deleteById(expectedId);
        Optional<MeasureEntity> maybeEntities = measureRepo.findById(expectedId);
        assertThat(maybeEntities).isEmpty();
    }

    @Test
    void shouldCheckAllFinding() {
        saveOtherCodes();
        List<MeasureEntity> entities = measureRepo.findAll();
        assertThat(entities.size()).isEqualTo(1 + OTHER_CODES.length);
    }

    @Test
    void shouldCheckSearching() {
        saveOtherCodes();
        String filter = "item";
        List<MeasureEntity> entities = measureRepo.search(filter);
        assertThat(entities.size()).isEqualTo(3);
    }

    @AfterEach
    void tearDown() {
        measureRepo.deleteAll();
    }

    private void saveOtherCodes() {
        Arrays.stream(OTHER_CODES).map(code -> {
            MeasureEntity currencyEntity = new MeasureEntity();
            currencyEntity.setCode(code);
            return currencyEntity;
        }).forEach(measureRepo::save);
    }

    private MeasureEntity createMeasureEntity(String code) {
        MeasureEntity currencyEntity = new MeasureEntity();
        currencyEntity.setCode(code);
        return currencyEntity;
    }
}
