package kpn.financecontroller.gui.views.building;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

import java.util.List;

public class BuildingForm extends EditForm<Building> {

    private final TextField name = new TextField("Name", "type name");
    private final ComboBox<Street> street = new ComboBox<>("Street");

    public BuildingForm(List<Street> streets) {
        super(new Binder<>(Building.class));

        addClassName("building-form");
        binder.bindInstanceFields(this);

        street.setItems(streets);
        street.setItemLabelGenerator(Street::getFullName);

        add(
                name,
                street,
                createButtonsLayout()
        );
    }

    @Override
    protected SaveFormEvent<EditForm<Building>, Building> createSaveEvent() {
        return new BuildingSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<Building>, Building> createDeleteEvent() {
        return new BuildingDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<Building>, Building> createCloseEvent() {
        return new BuildingCloseFormEvent(this);
    }

    public static class BuildingSaveFormEvent extends SaveFormEvent<EditForm<Building>, Building> {
        public BuildingSaveFormEvent(EditForm<Building> source, Building value) {
            super(source, value);
        }
    }

    public static class BuildingDeleteFormEvent extends DeleteFormEvent<EditForm<Building>, Building> {
        public BuildingDeleteFormEvent(EditForm<Building> source, Building value) {
            super(source, value);
        }
    }

    public static class BuildingCloseFormEvent extends CloseFormEvent<EditForm<Building>, Building> {
        public BuildingCloseFormEvent(EditForm<Building> source) {
            super(source);
        }
    }
}
