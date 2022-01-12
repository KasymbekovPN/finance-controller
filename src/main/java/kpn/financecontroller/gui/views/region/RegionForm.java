package kpn.financecontroller.gui.views.region;

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
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;

import java.util.List;

public class RegionForm extends FormLayout {

    private final TextField name = new TextField("Name", "type name...");
    private final ComboBox<Country> country = new ComboBox<>("Country");

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private final Binder<Region> binder = new Binder<>(Region.class);

     private Region region;

     public RegionForm(List<Country> countries) {
        addClassName("region-form");
        binder.bindInstanceFields(this);

        country.setItems(countries);
        country.setItemLabelGenerator(Country::getName);

        add(
                name,
                country,
                createButtonsLayout()
        );
     }

     public void setRegion(Region region) {
          this.region = region;
          binder.readBean(region);
     }

     private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());

        delete.addClickListener(event -> fireEvent(new RegionDeleteFormEvent(this, region)));
        close.addClickListener(event -> fireEvent(new RegionCloseFormEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
     }

    private void validateAndSave() {
      try{
           binder.writeBean(region);
           fireEvent(new RegionSaveFormEvent(this, region));
      } catch (ValidationException ex){
           ex.printStackTrace();
      }
    }

    @Override
    protected <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
         return getEventBus().addListener(eventType, listener);
    }

    public static class RegionSaveFormEvent extends SaveFormEvent<RegionForm, Region> {
        public RegionSaveFormEvent(RegionForm source, Region value) {
            super(source, value);
        }
    }

    public static class RegionDeleteFormEvent extends DeleteFormEvent<RegionForm, Region> {
        public RegionDeleteFormEvent(RegionForm source, Region value) {
            super(source, value);
        }
    }

    public static class RegionCloseFormEvent extends CloseFormEvent<RegionForm, Region> {
        public RegionCloseFormEvent(RegionForm source) {
            super(source);
        }
    }
}
