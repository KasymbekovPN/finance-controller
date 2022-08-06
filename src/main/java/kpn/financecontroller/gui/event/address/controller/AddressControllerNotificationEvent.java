package kpn.financecontroller.gui.event.address.controller;

import kpn.financecontroller.gui.controller.AddressViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;

public final class AddressControllerNotificationEvent extends NotificationEvent<AddressViewController> {
    public AddressControllerNotificationEvent(AddressViewController source, String value, NotificationType type) {
        super(source, value, type);
    }
}
