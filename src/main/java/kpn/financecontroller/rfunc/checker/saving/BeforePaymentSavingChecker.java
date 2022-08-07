package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Payment;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BeforePaymentSavingChecker extends AbstractBeforeSavingChecker<Payment> {
    @Override
    public Result<List<Payment>> apply(Payment value) {
        Result<List<Payment>> result = checkMandatoryAttributes(value);
        return result.isSuccess() ? checkAmountAndMeasure(value) : result;
    }

    private Result<List<Payment>> checkMandatoryAttributes(Payment value) {
        if (value.getProduct() == null){
            return ImmutableResult.<List<Payment>>fail("checking.domain.payment.product.isEmpty");
        }
        if (value.getCurrency() == null){
            return ImmutableResult.<List<Payment>>fail("checking.domain.payment.currency.isEmpty");
        }
        Float price = value.getPrice();
        if (price == null || price <= 0.0f){
            return ImmutableResult.<List<Payment>>fail("checking.domain.payment.price.isEmpty");
        }
        return value.getCreatedAt() == null
                ? ImmutableResult.<List<Payment>>fail("checking.domain.payment.createdAt.isEmpty")
                : ImmutableResult.<List<Payment>>ok(List.of(value));
    }

    private Result<List<Payment>> checkAmountAndMeasure(Payment value) {
        Float amount = value.getAmount();
        int state = (amount != null ? 1 : 0) + (value.getMeasure() != null ? 2 : 0);
        switch (state){
            case 1:
            case 2:
                return ImmutableResult.<List<Payment>>fail("checking.domain.payment.amountAndMeasure.notConsistent");
            case 3:
                if (amount <= 0.0f){
                    return ImmutableResult.<List<Payment>>fail("checking.domain.payment.amount.isEmpty");
                }
                break;
        }

        return ImmutableResult.<List<Payment>>ok(List.of(value));
    }
}
