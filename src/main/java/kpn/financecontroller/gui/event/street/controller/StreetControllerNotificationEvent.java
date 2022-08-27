package kpn.financecontroller.gui.event.street.controller;

import kpn.financecontroller.gui.controller.StreetViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;

public final class StreetControllerNotificationEvent extends NotificationEvent<StreetViewController> {
    public StreetControllerNotificationEvent(StreetViewController source, String value, NotificationType type) {
        super(source, value, type);
    }
}