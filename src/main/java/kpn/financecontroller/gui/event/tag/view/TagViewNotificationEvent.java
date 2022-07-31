package kpn.financecontroller.gui.event.tag.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.view.TagView;

public final class TagViewNotificationEvent extends NotificationEvent<TagView> {
    public TagViewNotificationEvent(TagView source, String value, Notifications type) {
        super(source, value, type);
    }
}
