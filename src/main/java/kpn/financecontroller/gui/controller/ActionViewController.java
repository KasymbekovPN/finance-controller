package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.Tag;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.gui.event.action.controller.ActionAfterDeletingEvent;
import kpn.financecontroller.gui.event.action.controller.ActionAfterSavingEvent;
import kpn.financecontroller.gui.event.action.controller.ActionControllerNotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;
import org.springframework.stereotype.Component;

@Component
@Tag(Tag.OBJECT)
public final class ActionViewController extends ViewController<Action> {
    @Override
    protected ComponentEvent<?> createAfterSavingEvent(Action domain) {
        return new ActionAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(Action domain) {
        return new ActionAfterDeletingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        String text = getTranslation(seed.getCode(), seed.getArgs());
        return new ActionControllerNotificationEvent(this, text, NotificationType.ERROR);
    }
}
