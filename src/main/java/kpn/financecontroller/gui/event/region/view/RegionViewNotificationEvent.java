package kpn.financecontroller.gui.event.region.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.RegionView;

public final class RegionViewNotificationEvent extends NotificationEvent<RegionView> {
    public RegionViewNotificationEvent(RegionView source, String value, NotificationType type) {
        super(source, value, type);
    }
}
