package kpn.financecontroller.gui.views.street;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

import java.util.List;

public class StreetForm extends EditForm<Street> {

    private final TextField name = new TextField();
    private final ComboBox<City> city = new ComboBox<>();

    public StreetForm(List<City> cities) {
        super(new Binder<>(Street.class));
        addClassName("street-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        city.setLabel(getTranslation("gui.city"));
        city.setItems(cities);
        city.setItemLabelGenerator(City::getInfo);

        add(
                name,
                city,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditForm<Street>, Street> createSaveEvent() {
        return new StreetSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<Street>, Street> createDeleteEvent() {
        return new StreetDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<Street>, Street> createCloseEvent() {
        return new StreetCloseFormEvent(this);
    }

    public static class StreetSaveFormEvent extends SaveFormEvent<EditForm<Street>, Street> {
        public StreetSaveFormEvent(EditForm<Street> source, Street value) {
            super(source, value);
        }
    }

    public static class StreetDeleteFormEvent extends DeleteFormEvent<EditForm<Street>, Street> {
        public StreetDeleteFormEvent(EditForm<Street> source, Street value) {
            super(source, value);
        }
    }

    public static class StreetCloseFormEvent extends CloseFormEvent<EditForm<Street>, Street> {
        public StreetCloseFormEvent(EditForm<Street> source) {
            super(source);
        }
    }
}
