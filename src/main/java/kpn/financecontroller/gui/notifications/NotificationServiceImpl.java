package kpn.financecontroller.gui.notifications;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import kpn.financecontroller.gui.event.NotificationEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * The NotificationServiceImpl is a notification service which creates Vaadin notification.
 * <p>
 * Optional yml-settings:
 * <pre>
 * {@code
 * notification:
 *   service:
 *     duration: 10
 *     icon:
 *       collection: lumo
 *       name: cross
 *     button:
 *       theme: LUMO_TERTIARY_INLINE
 *       attributes:
 *         arial-label: Close
 * }
 */
@Service
@Tag(Tag.OBJECT)
@ConfigurationProperties(prefix = "notification.service")
public final class NotificationServiceImpl extends Component implements NotificationService {
    private final Object lock = new Object();

    private Config config;

    public void setConfig(Config config) {
        System.out.println("setConfig calling");
        synchronized (lock){
            this.config = config;
        }
    }

    @Override
    public void notify(NotificationEvent<?> event) {
        NotificationBuilder builder = new NotificationBuilderImpl(event.getType())
                .text(getTranslation(event.getValue().getCode(), event.getValue().getArgs()));
        synchronized (lock){
            checkOrCreateConfigUnsafe();
            builder
                    .duration(config.getDuration())
                    .buttonIcon(new Icon(config.getIcon().getCollection(), config.getIcon().getName()))
                    .buttonThemeVariants(config.getButton().getTheme());
            config.getButton().getAttributes().forEach(builder::buttonAttribute);
        }
        builder
                .build()
                .open();
    }

    private void checkOrCreateConfigUnsafe() {
        if (config == null){
            config = new Config();
            config.setDuration(0);
            config.setIcon(null);
            config.setButton(null);
        }
    }

    @Getter
    public static class Config {
        private static final int DEFAULT_DURATION = 10_000;
        private int duration = DEFAULT_DURATION;
        private IconConfig icon = new IconConfig();
        private ButtonConfig button = new ButtonConfig();

        public void setDuration(int duration) {
            this.duration = duration > 0 ? duration * 1_000 : DEFAULT_DURATION;
        }

        public void setIcon(IconConfig icon) {
            if (icon != null){
                this.icon = icon;
            }
        }

        public void setButton(ButtonConfig button) {
            if (button != null){
                this.button = button;
            }
        }
    }

    @Getter
    private static class IconConfig {
        private static final String DEFAULT_COLLECTION = "lumo";
        private static final String DEFAULT_NAME = "cross";

        private String collection = DEFAULT_COLLECTION;
        private String name = DEFAULT_NAME;

        public void setCollection(String collection) {
            this.collection = collection != null && !collection.isEmpty() ? collection : DEFAULT_COLLECTION;
        }

        public void setName(String name) {
            this.name = name != null && !name.isEmpty() ? name : DEFAULT_NAME;
        }
    }

    @Getter
    private static class ButtonConfig {
        private static final ButtonVariant DEFAULT_THEME = ButtonVariant.LUMO_TERTIARY_INLINE;

        private ButtonVariant theme = DEFAULT_THEME;
        @Setter
        private Map<String, String> attributes = new HashMap<>();

        public void setTheme(String theme) {
            try{
                this.theme = ButtonVariant.valueOf(ButtonVariant.class, theme);
            }
            catch (RuntimeException ignored){}
        }
    }
}
