package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.data.services.dto.service.ProductDtoDecorator;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public final class ProductServiceWrapper extends BaseServiceWrapper<Product> {
    private static final Class<? extends Service<Long, Product, Predicate, Result<List<Product>>>> KEY = ProductDtoDecorator.class;

    @Override
    protected Class<? extends Service<Long, Product, Predicate, Result<List<Product>>>> getKey() {
        return KEY;
    }
}
