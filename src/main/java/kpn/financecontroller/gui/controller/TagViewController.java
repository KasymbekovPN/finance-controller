package kpn.financecontroller.gui.controller;

import com.vaadin.flow.component.ComponentEvent;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.event.tag.controller.TagControllerNotificationEvent;
import kpn.financecontroller.gui.event.tag.controller.TagAfterDeletingEvent;
import kpn.financecontroller.gui.event.tag.controller.TagAfterSavingEvent;
import kpn.lib.seed.Seed;

@org.springframework.stereotype.Component
@com.vaadin.flow.component.Tag(com.vaadin.flow.component.Tag.OBJECT)
public final class TagViewController extends ViewController<Tag> {
    @Override
    protected ComponentEvent<?> createAfterSavingEvent(Tag domain) {
        return new TagAfterSavingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createAfterDeletingEvent(Tag domain) {
        return new TagAfterDeletingEvent(this, domain);
    }

    @Override
    protected ComponentEvent<?> createNotificationEvent(Seed seed) {
        String text = getTranslation(seed.getCode(), seed.getArgs());
        return new TagControllerNotificationEvent(this, text, Notifications.ERROR);
    }
}
