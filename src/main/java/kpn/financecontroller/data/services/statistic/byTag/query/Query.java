package kpn.financecontroller.data.services.statistic.byTag.query;

import kpn.financecontroller.data.domains.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@ToString
final public class Query {
    private boolean beginTimeEnable;
    private LocalDate beginTime;
    private boolean endTimeEnable;
    private LocalDate endTime;
    private boolean forAllTags;
    private List<Tag> tags = new ArrayList<>();

    public Query(Query query) {
        this.beginTimeEnable = query.beginTimeEnable;
        this.beginTime = LocalDate.from(query.beginTime);
        this.endTimeEnable = query.endTimeEnable;
        this.endTime = LocalDate.from(query.endTime);
        this.forAllTags = query.forAllTags;
        this.tags = query.tags.stream().map(t -> {
            Tag tag = new Tag();
            tag.setId(t.getId());
            tag.setName(t.getName());
            return tag;
        }).collect(Collectors.toList());
    }

    // TODO: 24.05.2022 need ???
    public List<Long> getTagsAsIds(){
        return tags.stream().map(Tag::getId).collect(Collectors.toUnmodifiableList());
    }
}
