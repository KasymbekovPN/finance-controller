package kpn.financecontroller.gui.event.payment.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.PaymentView;
import kpn.lib.seed.Seed;

public final class PaymentViewNotificationEvent extends NotificationEvent<PaymentView> {
    public PaymentViewNotificationEvent(PaymentView source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
