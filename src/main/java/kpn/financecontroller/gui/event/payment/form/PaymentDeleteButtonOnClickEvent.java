package kpn.financecontroller.gui.event.payment.form;

import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.gui.event.DeleteEvent;
import kpn.financecontroller.gui.form.PaymentForm;

public final class PaymentDeleteButtonOnClickEvent extends DeleteEvent<PaymentForm, Payment> {
    public PaymentDeleteButtonOnClickEvent(PaymentForm source, Payment value) {
        super(source, value);
    }
}
