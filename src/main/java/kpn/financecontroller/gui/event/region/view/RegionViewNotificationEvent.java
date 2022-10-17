package kpn.financecontroller.gui.event.region.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.RegionView;
import kpn.lib.seed.Seed;

public final class RegionViewNotificationEvent extends NotificationEvent<RegionView> {
    public RegionViewNotificationEvent(RegionView source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
