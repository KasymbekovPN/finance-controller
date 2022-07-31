package kpn.financecontroller.gui.views.tag.event;

import kpn.financecontroller.gui.events.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.views.tag.controller.TagDomainController;

public final class TagDataControllerNotificationEvent extends NotificationEvent<TagDomainController> {
    public TagDataControllerNotificationEvent(TagDomainController source, String value, Notifications type) {
        super(source, value, type);
    }
}
