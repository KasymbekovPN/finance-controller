package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.gui.event.payment.controller.PaymentAfterDeletingEvent;
import kpn.financecontroller.gui.event.payment.controller.PaymentAfterSavingEvent;
import kpn.financecontroller.gui.event.payment.controller.PaymentControllerNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;
import org.springframework.stereotype.Component;

@Component
@Tag(Tag.OBJECT)
public final class PaymentViewController extends ViewController<Payment> {

    @Override
    protected ComponentEvent<?> createAfterSavingEvent(Payment domain) {
        return new PaymentAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(Payment domain) {
        return new PaymentAfterDeletingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        return new PaymentControllerNotificationEvent(this, seed, NotificationType.ERROR);
    }
}
