package kpn.financecontroller.gui.views.region;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

import java.util.List;

public class RegionForm extends EditForm<Region> {

    private final TextField name = new TextField("Name", "type name...");
    private final ComboBox<Country> country = new ComboBox<>("Country");

    public RegionForm(List<Country> countries) {
        super(new Binder<>(Region.class));
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

    @Override
    protected SaveFormEvent<EditForm<Region>, Region> createSaveEvent() {
        return new RegionSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<Region>, Region> createDeleteEvent() {
        return new RegionDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<Region>, Region> createCloseEvent() {
        return new RegionCloseFormEvent(this);
    }

    public static class RegionSaveFormEvent extends SaveFormEvent<EditForm<Region>, Region> {
        public RegionSaveFormEvent(EditForm<Region> source, Region value) {
            super(source, value);
        }
    }

    public static class RegionDeleteFormEvent extends DeleteFormEvent<EditForm<Region>, Region> {
        public RegionDeleteFormEvent(EditForm<Region> source, Region value) {
            super(source, value);
        }
    }

    public static class RegionCloseFormEvent extends CloseFormEvent<EditForm<Region>, Region> {
        public RegionCloseFormEvent(EditForm<Region> source) {
            super(source);
        }
    }
}
