package kpn.financecontroller.gui.event.address.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.NotificationType;
import kpn.financecontroller.gui.view.AddressView;
import kpn.lib.seed.Seed;

public final class AddressViewNotificationEvent extends NotificationEvent<AddressView> {
    public AddressViewNotificationEvent(AddressView source, Seed value, NotificationType type) {
        super(source, value, type);
    }
}
