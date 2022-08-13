package kpn.financecontroller.gui.event.action.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.ActionView;

public final class ActionViewNotificationEvent extends NotificationEvent<ActionView> {
    public ActionViewNotificationEvent(ActionView source, String value, NotificationType type) {
        super(source, value, type);
    }
}
