package kpn.financecontroller.gui.views.city;

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
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;

import java.util.List;

public class CityForm extends FormLayout {

    private final TextField name = new TextField("Name", "type name");
    private final ComboBox<Region> region = new ComboBox<>("Region");

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private final Binder<City> binder = new Binder<>(City.class);

    private City city;

    public CityForm(List<Region> regions) {
        addClassName("city-form");
        binder.bindInstanceFields(this);

        region.setItems(regions);
        region.setItemLabelGenerator(Region::getFullName);

        add(
                name,
                region,
                createButtonsLayout()
        );
    }

    public void setCity(City city) {
        this.city = city;
        binder.readBean(city);
    }


     private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());

        delete.addClickListener(event -> fireEvent(new CityDeleteFormEvent(this, city)));
        close.addClickListener(event -> fireEvent(new CityCloseFormEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
     }

    private void validateAndSave() {
      try{
           binder.writeBean(city);
           fireEvent(new CitySaveFormEvent(this, city));
      } catch (ValidationException ex){
           ex.printStackTrace();
      }
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
         return getEventBus().addListener(eventType, listener);
    }

    public static class CitySaveFormEvent extends SaveFormEvent<CityForm, City> {
        public CitySaveFormEvent(CityForm source, City value) {
            super(source, value);
        }
    }

    public static class CityDeleteFormEvent extends DeleteFormEvent<CityForm, City> {
        public CityDeleteFormEvent(CityForm source, City value) {
            super(source, value);
        }
    }

    public static class CityCloseFormEvent extends CloseFormEvent<CityForm, City> {
        public CityCloseFormEvent(CityForm source) {
            super(source);
        }
    }
}
