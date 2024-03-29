package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.gui.event.country.controller.CountryAfterDeletingEvent;
import kpn.financecontroller.gui.event.country.controller.CountryAfterSavingEvent;
import kpn.financecontroller.gui.event.country.controller.CountryControllerNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;
import org.springframework.stereotype.Component;

@Component
@Tag(Tag.OBJECT)
public final class CountryViewController extends ViewController<Country> {
    @Override
    protected ComponentEvent<?> createAfterSavingEvent(Country domain) {
        return new CountryAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(Country domain) {
        return new CountryAfterDeletingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        return new CountryControllerNotificationEvent(this, seed, NotificationType.ERROR);
    }
}
