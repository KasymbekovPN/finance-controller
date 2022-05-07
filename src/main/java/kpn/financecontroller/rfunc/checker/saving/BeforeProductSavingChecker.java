package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
final public class BeforeProductSavingChecker extends AbstractBeforeSavingChecker<Product> {

    @Override
    public Result<Product> apply(Product value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<Product>fail("checking.domain.product.name.isEmpty").build();
        }

        Set<Tag> tags = value.getTags();
        return tags == null || tags.isEmpty()
                ? ImmutableResult.<Product>fail("checking.domain.product.tags.isEmpty").build()
                : ImmutableResult.<Product>ok(value).build();
    }
}