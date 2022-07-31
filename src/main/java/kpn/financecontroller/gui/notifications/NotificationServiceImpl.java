package kpn.financecontroller.gui.notifications;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import kpn.financecontroller.gui.events.NotificationEvent;
import org.springframework.stereotype.Service;

@Service
public final class NotificationServiceImpl implements NotificationService {
    @Override
    public void notify(NotificationEvent<?> event) {
        new NotificationBuilderImpl(event.getType())
                .duration(60_000)
                .text(event.getValue())
                .buttonIcon(new Icon("lumo", "cross"))
                .buttonThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                .buttonAttribute("aria-label", "Close")
                .build()
                .open();
    }
}
