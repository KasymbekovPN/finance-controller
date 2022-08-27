package kpn.financecontroller.gui.event.country.controller;

import kpn.financecontroller.gui.controller.CountryViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;

public final class CountryControllerNotificationEvent extends NotificationEvent<CountryViewController> {
    public CountryControllerNotificationEvent(CountryViewController source, String value, NotificationType type) {
        super(source, value, type);
    }
}