package kpn.financecontroller.gui.event.product.controller;

import kpn.financecontroller.gui.controller.ProductViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;

public final class ProductControllerNotificationEvent extends NotificationEvent<ProductViewController> {
    public ProductControllerNotificationEvent(ProductViewController source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
