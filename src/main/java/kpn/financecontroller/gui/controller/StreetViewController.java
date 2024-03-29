package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.gui.event.street.controller.StreetAfterDeletingEventOnClickEvent;
import kpn.financecontroller.gui.event.street.controller.StreetAfterSavingEventOnClickEvent;
import kpn.financecontroller.gui.event.street.controller.StreetControllerNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;
import org.springframework.stereotype.Component;

@Component
@Tag(Tag.OBJECT)
public final class StreetViewController extends ViewController<Street> {
    @Override
    protected ComponentEvent<?> createAfterSavingEvent(Street domain) {
        return new StreetAfterSavingEventOnClickEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(Street domain) {
        return new StreetAfterDeletingEventOnClickEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        return new StreetControllerNotificationEvent(this, seed, NotificationType.ERROR);
    }
}
