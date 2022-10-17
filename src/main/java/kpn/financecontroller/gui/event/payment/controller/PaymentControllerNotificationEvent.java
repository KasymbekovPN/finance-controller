package kpn.financecontroller.gui.event.payment.controller;

import kpn.financecontroller.gui.controller.PaymentViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;

public final class PaymentControllerNotificationEvent extends NotificationEvent<PaymentViewController> {
    public PaymentControllerNotificationEvent(PaymentViewController source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
