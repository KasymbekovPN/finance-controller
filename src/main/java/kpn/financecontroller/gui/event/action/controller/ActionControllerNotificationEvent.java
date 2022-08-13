package kpn.financecontroller.gui.event.action.controller;

import kpn.financecontroller.gui.controller.ActionViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;

public final class ActionControllerNotificationEvent extends NotificationEvent<ActionViewController> {
    public ActionControllerNotificationEvent(ActionViewController source, String value, NotificationType type) {
        super(source, value, type);
    }
}
