package kpn.financecontroller.gui.event.seller.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.SellerView;

public final class SellerViewNotificationEvent extends NotificationEvent<SellerView> {
    public SellerViewNotificationEvent(SellerView source, String value, NotificationType type) {
        super(source, value, type);
    }
}
