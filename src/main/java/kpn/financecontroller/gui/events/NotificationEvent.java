package kpn.financecontroller.gui.events;

import com.vaadin.flow.component.Component;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.lib.seed.Seed;
import lombok.Getter;

@Getter
public abstract class NotificationEvent<F extends Component> extends FormEvent<F, String> {
    private final Notifications type;

    public NotificationEvent(F source, String value, Notifications type) {
        super(source, value);
        this.type = type;
    }
}
