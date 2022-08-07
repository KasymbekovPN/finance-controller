package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domain.Tag;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Function;

@Component
final public class TagsToNamesConverter implements Function<Collection<Tag>, String> {

    @Override
    public String apply(Collection<Tag> tags) {
        StringBuilder tagNamesSB = new StringBuilder();
        String delimiter = "";
        for (Tag tag : tags) {
            tagNamesSB.append(delimiter).append(tag.getName());
            delimiter = ", ";
        }
        return tagNamesSB.toString();
    }
}
