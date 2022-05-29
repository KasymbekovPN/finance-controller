package kpn.financecontroller.data.services.statistic.byTag;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.data.services.statistic.byTag.query.QueryOld;
import kpn.lib.result.Result;
import kpn.lib.seed.Seed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
final public class ByTagStatisticServiceImpl implements ByTagStatisticService<QueryOld, Seed> {
    private final Function<QueryOld, Result<Void>> checker;
    private final DTOService<Product, ProductEntity> productService;
    private final DTOService<Payment, PaymentEntity> paymentEntity;

    @Autowired
    public ByTagStatisticServiceImpl(Function<QueryOld, Result<Void>> checker,
                                     DTOService<Product, ProductEntity> productService,
                                     DTOService<Payment, PaymentEntity> paymentEntity) {
        this.checker = checker;
        this.productService = productService;
        this.paymentEntity = paymentEntity;
    }

    @Override
    public Seed calculate(QueryOld queryOld) {
        Result<Void> checkingResult = checker.apply(queryOld);
        if (checkingResult.isSuccess()){

            return null;
        }
        return checkingResult.getSeed();
    }
}
