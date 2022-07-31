package kpn.financecontroller.gui.notifications;

import kpn.financecontroller.gui.event.NotificationEvent;

public interface NotificationService {
    void notify(NotificationEvent<?> event);
}
