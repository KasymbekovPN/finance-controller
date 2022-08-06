package kpn.financecontroller.gui.event.country.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.CountryView;

public final class CountryViewNotificationEvent extends NotificationEvent<CountryView> {
    public CountryViewNotificationEvent(CountryView source, String value, NotificationType type) {
        super(source, value, type);
    }
}
