package kpn.financecontroller.gui.views.country;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domain.country.Country;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;

// TODO: 08.01.2022 make base class ???
public class CountryForm extends FormLayout {

    private final TextField name = new TextField("Name", "type name...");

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private final Binder<Country> binder = new Binder<>(Country.class);

    private Country country;

    public CountryForm() {
        addClassName("country-form");
        binder.bindInstanceFields(this);

        add(
                name,
                createButtonsLayout()
        );
    }

    public void setCountry(Country country) {
        this.country = country;
        binder.readBean(country);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());

        delete.addClickListener(event -> fireEvent(new CountryDeleteFormEvent(this, country)));
        close.addClickListener(event -> fireEvent(new CountryCloseFormEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
      try{
           binder.writeBean(country);
           fireEvent(new CountrySaveFormEvent(this, country));
      } catch (ValidationException ex){
           ex.printStackTrace();
      }
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
         return getEventBus().addListener(eventType, listener);
    }

    public static class CountrySaveFormEvent extends SaveFormEvent<CountryForm, Country> {
        public CountrySaveFormEvent(CountryForm source, Country value) {
            super(source, value);
        }
    }

    public static class CountryDeleteFormEvent extends DeleteFormEvent<CountryForm, Country> {
        public CountryDeleteFormEvent(CountryForm source, Country value) {
            super(source, value);
        }
    }

    public static class CountryCloseFormEvent extends CloseFormEvent<CountryForm, Country> {
        public CountryCloseFormEvent(CountryForm source) {
            super(source);
        }
    }
}
