package kpn.financecontroller.gui.event.action.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.ActionView;
import kpn.lib.seed.Seed;

public final class ActionViewNotificationEvent extends NotificationEvent<ActionView> {
    public ActionViewNotificationEvent(ActionView source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
