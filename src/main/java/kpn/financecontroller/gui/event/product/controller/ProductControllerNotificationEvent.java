package kpn.financecontroller.gui.event.product.controller;

import kpn.financecontroller.gui.controller.ProductViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;

public final class ProductControllerNotificationEvent extends NotificationEvent<ProductViewController> {
    public ProductControllerNotificationEvent(ProductViewController source, String value, Notifications type) {
        super(source, value, type);
    }
}
