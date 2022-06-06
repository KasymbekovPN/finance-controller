package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

@Component
final public class ProductTaskChecker implements Checker<ProductTask> {

    @Override
    public Result<ProductTask> check(ProductTask task) {
        return !task.isAllTags() && (task.getTags() == null || task.getTags().isEmpty())
                ? ImmutableResult.<ProductTask>bFail("stat.by.tag.product.checking.fail").value(task).build()
                : ImmutableResult.<ProductTask>ok(task);
    }
}
