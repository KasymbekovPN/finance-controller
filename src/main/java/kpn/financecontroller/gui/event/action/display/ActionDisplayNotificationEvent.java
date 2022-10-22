package kpn.financecontroller.gui.event.action.display;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.ActionDisplay;
import kpn.lib.seed.Seed;

public final class ActionDisplayNotificationEvent extends NotificationEvent<ActionDisplay> {
    public ActionDisplayNotificationEvent(ActionDisplay source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
