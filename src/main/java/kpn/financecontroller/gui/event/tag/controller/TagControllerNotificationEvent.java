package kpn.financecontroller.gui.event.tag.controller;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.controller.TagViewController;
import kpn.lib.seed.Seed;

public final class TagControllerNotificationEvent extends NotificationEvent<TagViewController> {
    public TagControllerNotificationEvent(TagViewController source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
