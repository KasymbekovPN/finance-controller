package kpn.financecontroller.gui.event.product.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.view.ProductView;

public final class ProductViewNotificationEvent extends NotificationEvent<ProductView> {
    public ProductViewNotificationEvent(ProductView source, String value, Notifications type) {
        super(source, value, type);
    }
}
