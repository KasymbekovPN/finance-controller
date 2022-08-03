package kpn.financecontroller.gui.event.payment.controller;

import kpn.financecontroller.gui.controller.PaymentViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;

public final class PaymentControllerNotificationEvent extends NotificationEvent<PaymentViewController> {
    public PaymentControllerNotificationEvent(PaymentViewController source, String value, Notifications type) {
        super(source, value, type);
    }
}
