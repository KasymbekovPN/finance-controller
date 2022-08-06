package kpn.financecontroller.gui.event.city.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.view.CityView;

public final class CityViewNotificationEvent extends NotificationEvent<CityView> {
    public CityViewNotificationEvent(CityView source, String value, Notifications type) {
        super(source, value, type);
    }
}
