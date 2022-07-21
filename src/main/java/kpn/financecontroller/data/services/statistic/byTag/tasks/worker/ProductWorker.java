package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
final public class ProductWorker implements Worker<ProductTask, Product> {
    private final Service<Long, Product, Predicate, Result<List<Product>>> service;
    private final Function<ProductTask, Predicate> converter;

    @Override
    public Result<List<Product>> execute(ProductTask task) {
        return  task.isAllTags() ? service.loader().all() : service.executor().execute(converter.apply(task));
    }
}
