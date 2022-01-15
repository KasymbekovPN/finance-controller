package kpn.financecontroller.gui.views.street;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;

import java.util.List;

public class StreetForm extends FormLayout {

    private final TextField name = new TextField("Name", "type name");
    private final ComboBox<City> city = new ComboBox<>("City");

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private final Binder<Street> binder = new Binder<>(Street.class);

    private Street street;

    public StreetForm(List<City> cities) {
        addClassName("street-form");
        binder.bindInstanceFields(this);

        city.setItems(cities);
        city.setItemLabelGenerator(City::getFullName);

        add(
                name,
                city,
                createButtonsLayout()
        );
    }

    public void setStreet(Street street) {
        this.street = street;
        binder.readBean(street);
    }

     private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new StreetDeleteFormEvent(this, street)));
        close.addClickListener(event -> fireEvent(new StreetCloseFormEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
     }

    private void validateAndSave() {
      try{
           binder.writeBean(street);
           fireEvent(new StreetSaveFormEvent(this, street));
      } catch (ValidationException ex){
           ex.printStackTrace();
      }
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
         return getEventBus().addListener(eventType, listener);
    }

    public static class StreetSaveFormEvent extends SaveFormEvent<StreetForm, Street> {
        public StreetSaveFormEvent(StreetForm source, Street value) {
            super(source, value);
        }
    }

    public static class StreetDeleteFormEvent extends DeleteFormEvent<StreetForm, Street> {
        public StreetDeleteFormEvent(StreetForm source, Street value) {
            super(source, value);
        }
    }

    public static class StreetCloseFormEvent extends CloseFormEvent<StreetForm, Street> {
        public StreetCloseFormEvent(StreetForm source) {
            super(source);
        }
    }
}
