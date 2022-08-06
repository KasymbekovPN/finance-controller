package kpn.financecontroller.gui.event.street.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.StreetView;

public final class StreetViewNotificationEvent extends NotificationEvent<StreetView> {
    public StreetViewNotificationEvent(StreetView source, String value, NotificationType type) {
        super(source, value, type);
    }
}
