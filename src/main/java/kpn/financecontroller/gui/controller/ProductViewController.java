package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.gui.event.product.controller.ProductAfterDeletingEvent;
import kpn.financecontroller.gui.event.product.controller.ProductAfterSavingEvent;
import kpn.financecontroller.gui.event.product.controller.ProductControllerNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;
import org.springframework.stereotype.Component;

@Component
@Tag(Tag.OBJECT)
public final class ProductViewController extends ViewController<Product> {
    @Override
    protected ComponentEvent<?> createAfterSavingEvent(Product domain) {
        return new ProductAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(Product domain) {
        return new ProductAfterDeletingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        return new ProductControllerNotificationEvent(this, seed, NotificationType.ERROR);
    }
}
