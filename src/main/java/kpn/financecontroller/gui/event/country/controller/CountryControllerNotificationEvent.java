package kpn.financecontroller.gui.event.country.controller;

import kpn.financecontroller.gui.controller.CountryViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;

public final class CountryControllerNotificationEvent extends NotificationEvent<CountryViewController> {
    public CountryControllerNotificationEvent(CountryViewController source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
