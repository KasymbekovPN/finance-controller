package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.data.domain.Tag;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public final class BeforeProductSavingChecker extends AbstractBeforeSavingChecker<Product> {
    @Override
    public Result<List<Product>> apply(Product value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<List<Product>>fail("checking.domain.product.name.isEmpty");
        }

        Set<Tag> tags = value.getTags();
        return tags == null || tags.isEmpty()
                ? ImmutableResult.<List<Product>>fail("checking.domain.product.tags.isEmpty")
                : ImmutableResult.<List<Product>>ok(List.of(value));
    }
}
