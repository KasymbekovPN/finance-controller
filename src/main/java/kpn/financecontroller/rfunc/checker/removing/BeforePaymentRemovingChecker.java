package kpn.financecontroller.rfunc.checker.removing;

import kpn.financecontroller.data.domains.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public final class BeforePaymentRemovingChecker extends AbstractBeforeRemovingChecker<Payment> {}
