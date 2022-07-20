// TODO: 19.07.2022 del
//package kpn.financecontroller.data.converters.entity2domain;
//
//import kpn.financecontroller.data.domains.Domain;
//import kpn.financecontroller.data.entities.AbstractEntity;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Queue;
//import java.util.stream.Collectors;
//
//class ConstructEntitiesToDomainsConverterTest {
//
//    @Test
//    void shouldCheckConversion() {
//        List<String> names = List.of("name0", "name1", "name2");
//        List<TestEntity> testEntities = names.stream().map(TestEntity::new).collect(Collectors.toList());
//        List<TestDomain> expectedDomains = names.stream().map(TestDomain::new).collect(Collectors.toList());
//
//        List<TestDomain> domains = new ConstructEntitiesToDomainsConverter<TestEntity, TestDomain>(TestDomain::new).apply(testEntities);
//        Assertions.assertThat(expectedDomains).isEqualTo(domains);
//    }
//
//    @RequiredArgsConstructor
//    @Getter
//    private static class TestEntity extends AbstractEntity{
//        private final String value;
//    }
//
//    @RequiredArgsConstructor
//    @EqualsAndHashCode
//    private static class TestDomain implements Domain{
//        private final String value;
//
//        public TestDomain(TestEntity entity) {
//            this.value = entity.getValue();
//        }
//
//        @Override
//        public String getInfo() {return null;}
//
//        @Override
//        public String get(Queue<String> path) {return null;}
//    }
//}