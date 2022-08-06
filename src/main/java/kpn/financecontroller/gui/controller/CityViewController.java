package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.gui.event.city.controller.CityAfterDeletingEvent;
import kpn.financecontroller.gui.event.city.controller.CityAfterSavingEvent;
import kpn.financecontroller.gui.event.city.controller.CityControllerNotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.lib.seed.Seed;
import org.springframework.stereotype.Component;

@Component
@Tag(Tag.OBJECT)
public final class CityViewController extends ViewController<City> {
    @Override
    protected ComponentEvent<?> createAfterSavingEvent(City domain) {
        return new CityAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(City domain) {
        return new CityAfterDeletingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        String text = getTranslation(seed.getCode(), seed.getArgs());
        return new CityControllerNotificationEvent(this, text, Notifications.ERROR);
    }
}
