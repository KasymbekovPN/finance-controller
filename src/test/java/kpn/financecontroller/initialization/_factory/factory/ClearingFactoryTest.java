// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._factory.factory;
//
//import kpn.financecontroller.initialization._task.task.Task;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ClearingFactoryTest {
//
//    @Test
//    void shouldCheckCreation() {
//        List<String> keys = List.of("key0", "key1", "key2");
//        List<ClearingFactory.InitItem> initItems = keys.stream().map(k -> {
//            return new ClearingFactory.InitItem(k, null);
//        }).collect(Collectors.toList());
//
//        ClearingFactory factory = new ClearingFactory(initItems);
//
//        for (String key : keys) {
//            Optional<Task> maybeTask = factory.getNextIfExist();
//            assertThat(maybeTask).isPresent();
//            assertThat(maybeTask.get().getKey()).isEqualTo(key);
//        }
//
//        Optional<Task> maybeTask = factory.getNextIfExist();
//        assertThat(maybeTask).isEmpty();
//    }
//}