package kpn.financecontroller.gui.notifications;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
final class NotificationBuilderImpl implements NotificationBuilder {

    private final NotificationType type;
    private int duration;
    private Icon icon;
    private ButtonVariant buttonVariant;
    private Map<String, String> attributes = new HashMap<>();
    private String text;

    @Override
    public NotificationBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public NotificationBuilder buttonIcon(Icon icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public NotificationBuilder buttonThemeVariants(ButtonVariant buttonVariant) {
        this.buttonVariant = buttonVariant;
        return this;
    }

    @Override
    public NotificationBuilder buttonAttribute(String attribute, String value) {
        attributes.put(attribute, value);
        return this;
    }

    @Override
    public NotificationBuilder text(String text) {
        this.text = text;
        return this;
    }

    @Override
    public Notification build() {
        Notification notification = new Notification("", duration);
        Div div = createDiv();
        Button button = createButton(notification);
        HorizontalLayout layout = createLayout(div, button);

        notification.add(layout);
        return notification;
    }

    private Div createDiv() {
        return new Div(new Text(String.format("[%s] %s", type.name(), text)));
    }

    private Button createButton(Notification notification) {
        Button button = new Button(icon);
        button.addThemeVariants(buttonVariant);
        attributes.forEach((key, value) -> button.getElement().setAttribute(key, value));
        button.addClickListener(event -> notification.close());

        return button;
    }

    private HorizontalLayout createLayout(Div div, Button button) {
        HorizontalLayout layout = new HorizontalLayout(div, button);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        return layout;
    }
}
