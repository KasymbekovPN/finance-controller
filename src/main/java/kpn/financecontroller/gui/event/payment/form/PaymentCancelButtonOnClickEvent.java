package kpn.financecontroller.gui.event.payment.form;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.gui.event.CancelEvent;
import kpn.financecontroller.gui.form.PaymentForm;

public final class PaymentCancelButtonOnClickEvent extends CancelEvent<PaymentForm, Payment> {
    public PaymentCancelButtonOnClickEvent(PaymentForm source) {
        super(source);
    }
}
