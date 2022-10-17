package kpn.financecontroller.gui.event.country.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.CountryView;
import kpn.lib.seed.Seed;

public final class CountryViewNotificationEvent extends NotificationEvent<CountryView> {
    public CountryViewNotificationEvent(CountryView source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
