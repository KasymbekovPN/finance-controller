package kpn.financecontroller.gui.views.geo.city;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

import java.util.List;

final public class CityForm extends EditForm<City> {

    private final TextField name = new TextField();
    private final ComboBox<Region> region = new ComboBox<>();

    public CityForm(List<Region> regions) {
        super(new Binder<>(City.class));
        addClassName("city-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        region.setLabel(getTranslation("gui.label.region"));
        region.setItems(regions);
        region.setItemLabelGenerator(Region::getInfo);

        add(
                name,
                region,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditForm<City>, City> createSaveEvent() {
        return new CitySaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<City>, City> createDeleteEvent() {
        return new CityDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<City>, City> createCloseEvent() {
        return new CityCloseFormEvent(this);
    }

    public static class CitySaveFormEvent extends SaveFormEvent<EditForm<City>, City> {
        public CitySaveFormEvent(EditForm<City> source, City value) {
            super(source, value);
        }
    }

    public static class CityDeleteFormEvent extends DeleteFormEvent<EditForm<City>, City> {
        public CityDeleteFormEvent(EditForm<City> source, City value) {
            super(source, value);
        }
    }

    public static class CityCloseFormEvent extends CloseFormEvent<EditForm<City>, City> {
        public CityCloseFormEvent(EditForm<City> source) {
            super(source);
        }
    }
}
