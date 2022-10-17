package kpn.financecontroller.gui.event.region.controller;

import kpn.financecontroller.gui.controller.RegionViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;

public final class RegionControllerNotificationEvent extends NotificationEvent<RegionViewController> {
    public RegionControllerNotificationEvent(RegionViewController source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
