package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.payment.Payment;
import org.springframework.stereotype.Component;

@Component
final public class BeforePaymentSavingChecker extends AbstractBeforeSavingChecker<Payment> {}
