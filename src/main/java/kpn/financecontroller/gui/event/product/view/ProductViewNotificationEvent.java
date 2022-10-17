package kpn.financecontroller.gui.event.product.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.ProductView;
import kpn.lib.seed.Seed;

public final class ProductViewNotificationEvent extends NotificationEvent<ProductView> {
    public ProductViewNotificationEvent(ProductView source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
