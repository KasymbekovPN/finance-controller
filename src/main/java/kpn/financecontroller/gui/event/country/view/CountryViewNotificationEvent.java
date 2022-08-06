package kpn.financecontroller.gui.event.country.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.view.CountryView;

public final class CountryViewNotificationEvent extends NotificationEvent<CountryView> {
    public CountryViewNotificationEvent(CountryView source, String value, Notifications type) {
        super(source, value, type);
    }
}
