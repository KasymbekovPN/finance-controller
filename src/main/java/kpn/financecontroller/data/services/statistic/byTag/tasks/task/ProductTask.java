package kpn.financecontroller.data.services.statistic.byTag.tasks.task;

import kpn.financecontroller.data.domains.tag.Tag;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
final public class ProductTask implements Task {
    private boolean allTags;
    private Set<Tag> tags;

    public static ProductTask copy(ProductTask t){
        ProductTask task = new ProductTask();
        task.setAllTags(t.isAllTags());
        task.setTags(t.getTags());
        return task;
    }
}
