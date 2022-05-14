package kpn.financecontroller.rfunc.checker.removing;

import kpn.financecontroller.data.domains.payment.Payment;
import org.springframework.stereotype.Component;

@Component
final public class BeforePaymentRemovingChecker extends AbstractBeforeRemovingChecker<Payment> {}
