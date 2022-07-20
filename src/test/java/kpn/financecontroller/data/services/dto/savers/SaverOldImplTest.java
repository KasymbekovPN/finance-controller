// TODO: 20.07.2022 del
//package kpn.financecontroller.data.services.dto.savers;
//
//import kpn.financecontroller.data.services.dto.utils.TestEntityOld;
//import kpn.financecontroller.data.services.dto.utils.TestModelOld;
//import kpn.financecontroller.data.services.dto.utils.TestRepoOld;
//import kpn.lib.result.Result;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.mockito.exceptions.base.MockitoException;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class SaverOldImplTest {
//
//    private static final String SAVER_NAME = "saver";
//
//    private static TestEntityOld throwEntity;
//    private static TestEntityOld testEntityOld;
//    private static TestModelOld testModelOld;
//    private static SaverOldImpl<TestModelOld, TestEntityOld, Long> saver;
//
//    @BeforeAll
//    static void beforeAll() {
//        throwEntity = new TestEntityOld();
//        throwEntity.setId(-1L);
//
//        testEntityOld = new TestEntityOld();
//        testEntityOld.setId(1L);
//
//        testModelOld = new TestModelOld(testEntityOld);
//
//        saver = new SaverOldImpl<>(createRepo(), TestModelOld::new, SAVER_NAME);
//    }
//
//    @Test
//    void shouldCheckSaving() {
//        Result<TestModelOld> result = saver.save(testEntityOld);
//        assertThat(result.isSuccess()).isTrue();
//        assertThat(result.getValue()).isEqualTo(testModelOld);
//    }
//
//    @Test
//    void shouldCheckWrongWaySaving() {
//        Result<TestModelOld> result = saver.save(throwEntity);
//        assertThat(result.isSuccess()).isFalse();
//        assertThat(result.getSeed().getCode()).isEqualTo("saver.saveImpl.fail");
//        assertThat(result.getSeed().getArgs()).isEqualTo(List.of(SAVER_NAME).toArray());
//    }
//
//    private static JpaRepository<TestEntityOld, Long> createRepo(){
//        TestRepoOld repo = Mockito.mock(TestRepoOld.class);
//
//        Mockito
//                .when(repo.save(throwEntity))
//                .thenThrow(new MockitoException(""));
//
//        Mockito
//                .when(repo.save(testEntityOld))
//                .thenReturn(testEntityOld);
//
//        return repo;
//    }
//}