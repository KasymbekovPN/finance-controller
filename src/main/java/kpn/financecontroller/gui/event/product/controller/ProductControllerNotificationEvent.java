package kpn.financecontroller.gui.event.product.controller;

import kpn.financecontroller.gui.controller.ProductViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;

public final class ProductControllerNotificationEvent extends NotificationEvent<ProductViewController> {
    public ProductControllerNotificationEvent(ProductViewController source, String value, NotificationType type) {
        super(source, value, type);
    }
}
