package kpn.financecontroller.converters;

import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// TODO: 27.02.2022 del ???
@Component
@Profile("dev")
public class TagConverter implements Converter<TagInitialEntity, TagEntity> {

    @Override
    public TagEntity convert(TagInitialEntity value) {
        TagEntity entity = new TagEntity();
        entity.setId(value.getId());
        entity.setName(value.getName());
        return entity;
    }
}
