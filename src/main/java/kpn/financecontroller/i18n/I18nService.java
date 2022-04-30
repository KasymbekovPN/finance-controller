package kpn.financecontroller.i18n;

import com.vaadin.flow.i18n.I18NProvider;

public interface I18nService extends I18NProvider {
    public String getTranslation(String key, Object... params);
}
