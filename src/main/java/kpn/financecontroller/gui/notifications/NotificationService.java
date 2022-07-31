package kpn.financecontroller.gui.notifications;

import kpn.financecontroller.gui.events.NotificationEvent;

public interface NotificationService {
    void notify(NotificationEvent<?> event);
}
