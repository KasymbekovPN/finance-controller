package kpn.financecontroller.gui.event.payment.form;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.form.PaymentForm;

public final class PaymentSaveButtonOnClickEvent extends SaveFormEvent<PaymentForm, Payment> {
    public PaymentSaveButtonOnClickEvent(PaymentForm source, Payment value) {
        super(source, value);
    }
}
