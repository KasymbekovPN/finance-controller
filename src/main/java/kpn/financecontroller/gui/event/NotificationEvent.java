package kpn.financecontroller.gui.event;

import com.vaadin.flow.component.Component;
import kpn.financecontroller.gui.notifications.NotificationType;
import lombok.Getter;

@Getter
public abstract class NotificationEvent<F extends Component> extends GuiEvent<F, String> {
    private final NotificationType type;

    public NotificationEvent(F source, String value, NotificationType type) {
        super(source, value);
        this.type = type;
    }
}
