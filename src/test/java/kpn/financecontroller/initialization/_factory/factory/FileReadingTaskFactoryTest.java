// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._factory.factory;
//
//import kpn.financecontroller.initialization._task.task.Task;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class FileReadingTaskFactoryTest {
//
//    @Test
//    void shouldCheckCreation() {
//        List<String> keys = List.of("key0", "key1", "key2");
//        FileReadingTaskFactory factory = new FileReadingTaskFactory(keys);
//
//        for (String key : keys) {
//            Optional<Task> maybeTask = factory.getNextIfExist();
//            assertThat(maybeTask).isPresent();
//            assertThat(key).isEqualTo(maybeTask.get().getKey());
//        }
//
//        Optional<Task> maybeTask = factory.getNextIfExist();
//        assertThat(maybeTask).isEmpty();
//    }
//}
