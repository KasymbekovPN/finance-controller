package kpn.financecontroller.gui.event.address.view;

import kpn.financecontroller.gui.event.NotificationEvent;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.view.AddressView;

public final class AddressViewNotificationEvent extends NotificationEvent<AddressView> {
    public AddressViewNotificationEvent(AddressView source, String value, Notifications type) {
        super(source, value, type);
    }
}
