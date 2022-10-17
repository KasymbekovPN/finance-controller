package kpn.financecontroller.gui.event.seller.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.SellerView;
import kpn.lib.seed.Seed;

public final class SellerViewNotificationEvent extends NotificationEvent<SellerView> {
    public SellerViewNotificationEvent(SellerView source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
