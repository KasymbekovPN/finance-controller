package kpn.financecontroller.gui.event.payment.controller;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.gui.controller.PaymentViewController;
import kpn.financecontroller.gui.event.SaveFormEvent;

public final class PaymentAfterSavingEvent extends SaveFormEvent<PaymentViewController, Payment> {
    public PaymentAfterSavingEvent(PaymentViewController source, Payment value) {
        super(source, value);
    }
}
