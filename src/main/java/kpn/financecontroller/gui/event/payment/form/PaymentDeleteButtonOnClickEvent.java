package kpn.financecontroller.gui.event.payment.form;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.form.PaymentForm;

public final class PaymentDeleteButtonOnClickEvent extends DeleteFormEvent<PaymentForm, Payment> {
    public PaymentDeleteButtonOnClickEvent(PaymentForm source, Payment value) {
        super(source, value);
    }
}
