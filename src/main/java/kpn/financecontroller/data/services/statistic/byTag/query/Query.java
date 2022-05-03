package kpn.financecontroller.data.services.statistic.byTag.query;

import kpn.financecontroller.data.domains.tag.Tag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
final public class Query {
    private boolean beginTimeEnable;
    private LocalDate beginTime;
    private boolean endTimeEnable;
    private LocalDate endTime;
    private boolean forAllTags;
    private List<Tag> tags = new ArrayList<>();

    public List<Long> getTagsAsIds(){
        return tags.stream().map(Tag::getId).collect(Collectors.toUnmodifiableList());
    }
}
