// TODO: 16.03.2022 del
//package kpn.financecontroller.converters;
//
//import kpn.financecontroller.data.entities.tag.TagEntity;
//import kpn.financecontroller.initialization._old.old.entities.TagInitialEntity;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class TagConverterTest {
//
//    @Test
//    void checkConversion() {
//        TagInitialEntity initialEntity = new TagInitialEntity();
//        long id = 123L;
//        initialEntity.setId(id);
//        String name = "name";
//        initialEntity.setName(name);
//
//        TagConverter converter = new TagConverter();
//        TagEntity entity = converter.convert(initialEntity);
//        assertThat(initialEntity.getId()).isEqualTo(entity.getId());
//        assertThat(initialEntity.getName()).isEqualTo(entity.getName());
//    }
//}