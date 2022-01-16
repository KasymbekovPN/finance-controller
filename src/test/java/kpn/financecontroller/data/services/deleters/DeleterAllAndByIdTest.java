package kpn.financecontroller.data.services.deleters;

import kpn.financecontroller.result.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeleterAllAndByIdTest {

    private static DeleterAllAndById<Object, ModelEntity, Long> deleter;

    @BeforeAll
    static void beforeAll() {
        deleter = new DeleterAllAndById<>(createRepo());
    }

    @Test
    void shouldCheckById() {
        long expectedId = 1L;
        Result<Void> result = deleter.byId(expectedId);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("deleter.deleteById.fail");
        assertThat(result.getArgs()).isEqualTo(List.of("DeleterAllAndById", expectedId).toArray());
    }

    @Test
    void shouldCheckBy() {
        String attribute = "attribute";
        String value = "value";
        Result<Void> result = deleter.by(attribute, value);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("deleter.by.attribute.disallowed");
        assertThat(result.getArgs()).isEqualTo(List.of("DeleterAllAndById", attribute, value).toArray());
    }

    @Test
    void shouldCheckAll() {
        Result<Void> result = deleter.all();
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getCode()).isEqualTo("deleter.deleteAll.fail");
    }

    private static JpaRepository<ModelEntity, Long> createRepo(){
        Repo repo = Mockito.mock(Repo.class);

        Mockito
                .doThrow(MockitoException.class)
                .when(repo)
                .deleteById(Mockito.any(Long.class));

        Mockito
                .doThrow(MockitoException.class)
                .when(repo)
                .deleteAll();

        return repo;
    }

    private interface Repo extends JpaRepository<ModelEntity, Long>{}

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    private static class Model{
        private Long id;
        private String value;

        public Model(ModelEntity entity) {
            id = entity.getId();
            value = entity.getValue();
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @Entity(name = "model")
    private static class ModelEntity{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String value;

        public ModelEntity(Model model) {
            id = model.getId();
            value = model.getValue();
        }
    }
}