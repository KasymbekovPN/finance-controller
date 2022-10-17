package kpn.financecontroller.gui.event.street.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.StreetView;
import kpn.lib.seed.Seed;

public final class StreetViewNotificationEvent extends NotificationEvent<StreetView> {
    public StreetViewNotificationEvent(StreetView source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
