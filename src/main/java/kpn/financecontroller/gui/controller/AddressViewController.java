package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.gui.event.address.controller.AddressAfterSavingEvent;
import kpn.financecontroller.gui.event.address.controller.AddressControllerNotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.lib.seed.Seed;
import org.springframework.stereotype.Component;

@Component
@Tag(Tag.OBJECT)
public final class AddressViewController extends ViewController<Address> {
    @Override
    protected ComponentEvent<?> createAfterSavingEvent(Address domain) {
        return new AddressAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(Address domain) {
        return new AddressAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        String text = getTranslation(seed.getCode(), seed.getArgs());
        return new AddressControllerNotificationEvent(this, text, Notifications.ERROR);
    }
}
