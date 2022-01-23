package kpn.financecontroller.i18n;

import com.vaadin.flow.i18n.I18NProvider;
import kpn.financecontroller.message.LocaledMessageSeed;

public interface I18nService extends I18NProvider {
    String getTranslation(LocaledMessageSeed seed);
    String getTranslation(String key, Object... params);
}
