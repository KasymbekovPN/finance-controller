package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Product;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public final class ProductServiceWrapper extends BaseServiceWrapper<Product> {
    public static Service<Long, Product, Predicate, Result<List<Product>>> SERVICE;

    @Override
    protected Result<List<Product>> createFailResult() {
        return ImmutableResult.<List<Product>>fail("service.product.null");
    }

    @Override
    protected Service<Long, Product, Predicate, Result<List<Product>>> getService() {
        return SERVICE;
    }
}
