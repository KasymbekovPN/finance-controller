package kpn.financecontroller.data.services.statistic.byTag.query;

import kpn.financecontroller.data.domains.tag.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// TODO: 29.05.2022 del
@NoArgsConstructor
@Getter
@Setter
@ToString
final public class QueryOld {
    private boolean beginTimeEnable;
    private LocalDate beginTime;
    private boolean endTimeEnable;
    private LocalDate endTime;
    private boolean forAllTags;
    private List<Tag> tags = new ArrayList<>();

    public QueryOld(QueryOld queryOld) {
        this.beginTimeEnable = queryOld.beginTimeEnable;
        this.beginTime = LocalDate.from(queryOld.beginTime);
        this.endTimeEnable = queryOld.endTimeEnable;
        this.endTime = LocalDate.from(queryOld.endTime);
        this.forAllTags = queryOld.forAllTags;
        this.tags = queryOld.tags.stream().map(t -> {
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
