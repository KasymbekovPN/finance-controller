package kpn.financecontroller.gui.event.action.editor;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.ActionEditor;

public final class ActionEditorNotificationEvent extends NotificationEvent<ActionEditor> {
    public ActionEditorNotificationEvent(ActionEditor source, String value, NotificationType type) {
        super(source, value, type);
    }
}
