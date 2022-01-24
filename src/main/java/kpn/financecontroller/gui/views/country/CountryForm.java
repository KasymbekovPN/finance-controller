package kpn.financecontroller.gui.views.country;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

public class CountryForm extends EditForm<Country> {

    private final TextField name;

    public CountryForm() {
        super(new Binder<>(Country.class));
        addClassName("country-form");
        binder.bindInstanceFields(this);

        name = new TextField(getTranslation("gui.name"), getTranslation("gui.placeholder.type-name"));

        add(
                name,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditForm<Country>, Country> createSaveEvent() {
        return new CountrySaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<Country>, Country> createDeleteEvent() {
        return new CountryDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<Country>, Country> createCloseEvent() {
        return new CountryCloseFormEvent(this);
    }

    public static class CountrySaveFormEvent extends SaveFormEvent<EditForm<Country>, Country> {
        public CountrySaveFormEvent(EditForm<Country> source, Country value) {
            super(source, value);
        }
    }

    public static class CountryDeleteFormEvent extends DeleteFormEvent<EditForm<Country>, Country> {
        public CountryDeleteFormEvent(EditForm<Country> source, Country value) {
            super(source, value);
        }
    }

    public static class CountryCloseFormEvent extends CloseFormEvent<EditForm<Country>, Country> {
        public CountryCloseFormEvent(EditForm<Country> source) {
            super(source);
        }
    }
}
