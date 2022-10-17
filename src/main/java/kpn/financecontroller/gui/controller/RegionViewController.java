package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.gui.event.region.controller.RegionAfterDeletingEvent;
import kpn.financecontroller.gui.event.region.controller.RegionAfterSavingEvent;
import kpn.financecontroller.gui.event.region.controller.RegionControllerNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;
import org.springframework.stereotype.Component;

@Component
@Tag(Tag.OBJECT)
public final class RegionViewController extends ViewController<Region>{
    @Override
    protected ComponentEvent<?> createAfterSavingEvent(Region domain) {
        return new RegionAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(Region domain) {
        return new RegionAfterDeletingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        return new RegionControllerNotificationEvent(this, seed, NotificationType.ERROR);
    }
}
