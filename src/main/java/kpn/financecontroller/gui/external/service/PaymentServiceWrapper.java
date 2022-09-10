package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.data.services.dto.service.PaymentDtoDecorator;
import kpn.financecontroller.annotation.External;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

@External
public final class PaymentServiceWrapper extends BaseServiceWrapper<Payment> {
    private static final Class<? extends Service<Long, Payment, Predicate, Result<List<Payment>>>> KEY = PaymentDtoDecorator.class;

    @Override
    protected Class<? extends Service<Long, Payment, Predicate, Result<List<Payment>>>> getKey() {
        return KEY;
    }
}
