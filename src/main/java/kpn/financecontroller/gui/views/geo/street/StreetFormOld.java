package kpn.financecontroller.gui.views.geo.street;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditFormOld;

import java.util.List;

final public class StreetFormOld extends EditFormOld<Street> {

    private final TextField name = new TextField();
    private final ComboBox<City> city = new ComboBox<>();

    public StreetFormOld(List<City> cities) {
        super(new Binder<>(Street.class));
        addClassName("street-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.label.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        city.setLabel(getTranslation("gui.label.city"));
        city.setItems(cities);
        city.setItemLabelGenerator(City::getInfo);

        add(
                name,
                city,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditFormOld<Street>, Street> createSaveEvent() {
        return new StreetSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditFormOld<Street>, Street> createDeleteEvent() {
        return new StreetDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditFormOld<Street>, Street> createCloseEvent() {
        return new StreetCloseFormEvent(this);
    }

    public static class StreetSaveFormEvent extends SaveFormEvent<EditFormOld<Street>, Street> {
        public StreetSaveFormEvent(EditFormOld<Street> source, Street value) {
            super(source, value);
        }
    }

    public static class StreetDeleteFormEvent extends DeleteFormEvent<EditFormOld<Street>, Street> {
        public StreetDeleteFormEvent(EditFormOld<Street> source, Street value) {
            super(source, value);
        }
    }

    public static class StreetCloseFormEvent extends CloseFormEvent<EditFormOld<Street>, Street> {
        public StreetCloseFormEvent(EditFormOld<Street> source) {
            super(source);
        }
    }
}
