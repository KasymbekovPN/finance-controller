package kpn.financecontroller.gui.event.address.controller;

import kpn.financecontroller.gui.controller.AddressViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;

public final class AddressControllerNotificationEvent extends NotificationEvent<AddressViewController> {
    public AddressControllerNotificationEvent(AddressViewController source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
