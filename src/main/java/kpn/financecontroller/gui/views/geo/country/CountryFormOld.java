package kpn.financecontroller.gui.views.geo.country;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditFormOld;

final public class CountryFormOld extends EditFormOld<Country> {

    private final TextField name = new TextField();

    public CountryFormOld() {
        super(new Binder<>(Country.class));
        addClassName("country-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        add(
                name,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditFormOld<Country>, Country> createSaveEvent() {
        return new CountrySaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditFormOld<Country>, Country> createDeleteEvent() {
        return new CountryDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditFormOld<Country>, Country> createCloseEvent() {
        return new CountryCloseFormEvent(this);
    }

    public static class CountrySaveFormEvent extends SaveFormEvent<EditFormOld<Country>, Country> {
        public CountrySaveFormEvent(EditFormOld<Country> source, Country value) {
            super(source, value);
        }
    }

    public static class CountryDeleteFormEvent extends DeleteFormEvent<EditFormOld<Country>, Country> {
        public CountryDeleteFormEvent(EditFormOld<Country> source, Country value) {
            super(source, value);
        }
    }

    public static class CountryCloseFormEvent extends CloseFormEvent<EditFormOld<Country>, Country> {
        public CountryCloseFormEvent(EditFormOld<Country> source) {
            super(source);
        }
    }
}
