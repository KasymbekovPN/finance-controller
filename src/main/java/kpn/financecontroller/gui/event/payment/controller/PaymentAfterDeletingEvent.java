package kpn.financecontroller.gui.event.payment.controller;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.gui.controller.PaymentViewController;
import kpn.financecontroller.gui.event.DeleteEvent;

public final class PaymentAfterDeletingEvent extends DeleteEvent<PaymentViewController, Payment> {
    public PaymentAfterDeletingEvent(PaymentViewController source, Payment value) {
        super(source, value);
    }
}
