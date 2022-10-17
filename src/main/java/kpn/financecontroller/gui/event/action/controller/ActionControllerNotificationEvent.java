package kpn.financecontroller.gui.event.action.controller;

import kpn.financecontroller.gui.controller.ActionViewController;
import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.lib.seed.Seed;

public final class ActionControllerNotificationEvent extends NotificationEvent<ActionViewController> {
    public ActionControllerNotificationEvent(ActionViewController source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
