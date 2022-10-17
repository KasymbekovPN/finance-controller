package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.gui.event.seller.controller.SellerAfterDeletingEvent;
import kpn.financecontroller.gui.event.seller.controller.SellerAfterSavingEvent;
import kpn.financecontroller.gui.event.seller.controller.SellerControllerNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;
import org.springframework.stereotype.Component;

@Component
@Tag(Tag.OBJECT)
public final class SellerViewController extends ViewController<Seller> {
    @Override
    protected ComponentEvent<?> createAfterSavingEvent(Seller domain) {
        return new SellerAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(Seller domain) {
        return new SellerAfterDeletingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        return new SellerControllerNotificationEvent(this, seed, NotificationType.ERROR);
    }
}
