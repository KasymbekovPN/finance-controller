package kpn.financecontroller.gui.event.action.editor;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.ActionEditor;
import kpn.lib.seed.Seed;

public final class ActionEditorNotificationEvent extends NotificationEvent<ActionEditor> {
    public ActionEditorNotificationEvent(ActionEditor source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
