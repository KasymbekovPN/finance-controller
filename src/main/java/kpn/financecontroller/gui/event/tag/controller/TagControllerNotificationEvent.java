package kpn.financecontroller.gui.event.tag.controller;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.controller.TagViewController;

public final class TagControllerNotificationEvent extends NotificationEvent<TagViewController> {
    public TagControllerNotificationEvent(TagViewController source, String value, Notifications type) {
        super(source, value, type);
    }
}
