package kpn.financecontroller.gui.notifications;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;

public interface NotificationBuilder {
    NotificationBuilder duration(int duration);
    NotificationBuilder buttonIcon(Icon icon);
    NotificationBuilder buttonThemeVariants(ButtonVariant buttonVariant);
    NotificationBuilder buttonAttribute(String attribute, String value);
    NotificationBuilder text(String text);
    Notification build();
}
