package kpn.financecontroller.gui.views.tag.event;

import kpn.financecontroller.gui.events.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.views.tag.TagView;

public final class TagViewNotificationEvent extends NotificationEvent<TagView> {
    public TagViewNotificationEvent(TagView source, String value, Notifications type) {
        super(source, value, type);
    }
}
