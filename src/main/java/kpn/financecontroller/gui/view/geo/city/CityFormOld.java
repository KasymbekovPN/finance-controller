package kpn.financecontroller.gui.view.geo.city;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.event.CloseFormEvent;
import kpn.financecontroller.gui.event.DeleteFormEvent;
import kpn.financecontroller.gui.event.SaveFormEvent;
import kpn.financecontroller.gui.view.EditFormOld;

import java.util.List;

final public class CityFormOld extends EditFormOld<City> {

    private final TextField name = new TextField();
    private final ComboBox<Region> region = new ComboBox<>();

    public CityFormOld(List<Region> regions) {
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
    protected SaveFormEvent<EditFormOld<City>, City> createSaveEvent() {
        return new CitySaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditFormOld<City>, City> createDeleteEvent() {
        return new CityDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditFormOld<City>, City> createCloseEvent() {
        return new CityCloseFormEvent(this);
    }

    public static class CitySaveFormEvent extends SaveFormEvent<EditFormOld<City>, City> {
        public CitySaveFormEvent(EditFormOld<City> source, City value) {
            super(source, value);
        }
    }

    public static class CityDeleteFormEvent extends DeleteFormEvent<EditFormOld<City>, City> {
        public CityDeleteFormEvent(EditFormOld<City> source, City value) {
            super(source, value);
        }
    }

    public static class CityCloseFormEvent extends CloseFormEvent<EditFormOld<City>, City> {
        public CityCloseFormEvent(EditFormOld<City> source) {
            super(source);
        }
    }
}
