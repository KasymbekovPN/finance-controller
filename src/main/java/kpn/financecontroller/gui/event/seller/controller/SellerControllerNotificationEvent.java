package kpn.financecontroller.gui.event.seller.controller;

import kpn.financecontroller.gui.controller.SellerViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;

public final class SellerControllerNotificationEvent extends NotificationEvent<SellerViewController> {
    public SellerControllerNotificationEvent(SellerViewController source, String value, NotificationType type) {
        super(source, value, type);
    }
}
