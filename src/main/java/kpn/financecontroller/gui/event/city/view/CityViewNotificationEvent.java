package kpn.financecontroller.gui.event.city.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.CityView;
import kpn.lib.seed.Seed;

public final class CityViewNotificationEvent extends NotificationEvent<CityView> {
    public CityViewNotificationEvent(CityView source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
