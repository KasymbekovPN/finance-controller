package kpn.financecontroller.gui.event.payment.controller;

import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.gui.controller.PaymentViewController;
import kpn.financecontroller.gui.event.SaveEvent;

public final class PaymentAfterSavingEvent extends SaveEvent<PaymentViewController, Payment> {
    public PaymentAfterSavingEvent(PaymentViewController source, Payment value) {
        super(source, value);
    }
}
