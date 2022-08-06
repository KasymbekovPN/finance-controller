package kpn.financecontroller.gui.event.payment.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.PaymentView;

public final class PaymentViewNotificationEvent extends NotificationEvent<PaymentView> {
    public PaymentViewNotificationEvent(PaymentView source, String value, NotificationType type) {
        super(source, value, type);
    }
}
