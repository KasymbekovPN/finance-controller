package kpn.financecontroller.gui.event.region.controller;

import kpn.financecontroller.gui.controller.RegionViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;

public final class RegionControllerNotificationEvent extends NotificationEvent<RegionViewController> {
    public RegionControllerNotificationEvent(RegionViewController source, String value, Notifications type) {
        super(source, value, type);
    }
}
