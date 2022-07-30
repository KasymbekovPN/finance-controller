package kpn.financecontroller.gui.views.geo.region;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditFormOld;

import java.util.List;

final public class RegionFormOld extends EditFormOld<Region> {

    private final TextField name = new TextField();
    private final ComboBox<Country> country = new ComboBox<>();

    public RegionFormOld(List<Country> countries) {
        super(new Binder<>(Region.class));
        addClassName("region-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));
        country.setLabel(getTranslation("gui.label.country"));

        country.setItems(countries);
        country.setItemLabelGenerator(Country::getName);

        add(
                name,
                country,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditFormOld<Region>, Region> createSaveEvent() {
        return new RegionSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditFormOld<Region>, Region> createDeleteEvent() {
        return new RegionDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditFormOld<Region>, Region> createCloseEvent() {
        return new RegionCloseFormEvent(this);
    }

    public static class RegionSaveFormEvent extends SaveFormEvent<EditFormOld<Region>, Region> {
        public RegionSaveFormEvent(EditFormOld<Region> source, Region value) {
            super(source, value);
        }
    }

    public static class RegionDeleteFormEvent extends DeleteFormEvent<EditFormOld<Region>, Region> {
        public RegionDeleteFormEvent(EditFormOld<Region> source, Region value) {
            super(source, value);
        }
    }

    public static class RegionCloseFormEvent extends CloseFormEvent<EditFormOld<Region>, Region> {
        public RegionCloseFormEvent(EditFormOld<Region> source) {
            super(source);
        }
    }
}
