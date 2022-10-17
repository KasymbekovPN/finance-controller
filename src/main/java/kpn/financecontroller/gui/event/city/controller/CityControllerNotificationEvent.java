package kpn.financecontroller.gui.event.city.controller;

import kpn.financecontroller.gui.controller.CityViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;

public final class CityControllerNotificationEvent extends NotificationEvent<CityViewController> {
    public CityControllerNotificationEvent(CityViewController source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
