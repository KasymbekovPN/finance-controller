package kpn.financecontroller.data.services.measure;

import kpn.financecontroller.data.domains.measure.Measure;
import kpn.financecontroller.data.entities.measure.MeasureEntity;
import kpn.financecontroller.data.repos.measure.MeasureRepo;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class MeasureServiceTest {

    private static final String FIRST_CODE = "kg";
    private static final String SECOND_CODE = "gr";

    private final MeasureService measureService;
    private final MeasureRepo measureRepo;

    private Long expectedId;

    @Autowired
    public MeasureServiceTest(MeasureService measureService, MeasureRepo measureRepo) {
        this.measureService = measureService;
        this.measureRepo = measureRepo;
    }

    @BeforeEach
    void setUp() {
        expectedId = measureRepo.save(createMeasureEntity(FIRST_CODE)).getId();
    }

    @Test
    void shouldCheckSaving() {
        Result<Measure> result = measureService.save(createMeasureEntity(SECOND_CODE));
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
    }

    @Test
    void shouldCheckFailSavingAttempt() {
        Result<Measure> result = measureService.save(createMeasureEntity(FIRST_CODE));
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("service.measure.save.fail");
    }

    @Test
    void shouldCheckLoadingById() {
        Result<Measure> result = measureService.loadById(expectedId);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().getCode()).isEqualTo(FIRST_CODE);
    }

    @Test
    void shouldCheckFailLoadingAttemptById() {
        long expectedId = -1L;
        Result<Measure> result = measureService.loadById(expectedId);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("service.measure.loadById.noOne");
        assertThat(result.getArgs()).isEqualTo(List.of(expectedId).toArray());
    }

    @Test
    void shouldCheckDeleting() {
        measureService.deleteById(expectedId);
        Result<Measure> result = measureService.loadById(expectedId);
        assertThat(result.getSuccess()).isFalse();
    }

    @Test
    void shouldCheckFindAll() {
        Result<List<Measure>> result = measureService.findAll();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().size()).isEqualTo(1);
    }

    @Test
    void shouldCheckSearchingByFilter() {
        String filter = "k";
        Result<List<Measure>> result = measureService.search(filter);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue().size()).isEqualTo(1);
    }

    @AfterEach
    void tearDown() {
        measureRepo.deleteAll();
    }

    private MeasureEntity createMeasureEntity(String code) {
        MeasureEntity measureEntity = new MeasureEntity();
        measureEntity.setCode(code);
        return measureEntity;
    }
}
