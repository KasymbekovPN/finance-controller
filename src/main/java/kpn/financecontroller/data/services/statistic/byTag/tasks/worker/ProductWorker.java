package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;

import com.querydsl.core.types.dsl.BooleanExpression;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.entities.tag.QTagEntity;
import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.lib.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
final public class ProductWorker implements Worker<ProductTask, Product> {

    private final DTOService<Product, ProductEntity> service;
    // TODO: 02.06.2022 add task to expression converter

    @Override
    public Result<List<Product>> execute(ProductTask task) {
        if (task.isAllTags()){
            return service.loader().all();
        }

        Set<Long> tagIds = task.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        BooleanExpression expression = QTagEntity.tagEntity.id.in(tagIds);
        return service.executor().execute(expression);
    }
}
