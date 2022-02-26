package kpn.financecontroller.gui.views.place;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.domains.place.Place;
import kpn.financecontroller.gui.events.CloseFormEvent;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;

import java.util.List;

public class PlaceForm extends EditForm<Place> {

    private final Button saveWithoutBuilding = new Button();

    private final TextField name = new TextField();
    private final Checkbox online = new Checkbox();
    private final ComboBox<Building> building = new ComboBox<>();

    public PlaceForm(List<Building> buildings) {
        super(new Binder<>(Place.class));

        addClassName("place-form");
        binder.bindInstanceFields(this);

        name.setLabel(getTranslation("gui.name"));
        name.setPlaceholder(getTranslation("gui.placeholder.type-name"));

        online.setLabel(getTranslation("gui.online"));

        building.setLabel(getTranslation("gui.building"));
        building.setItems(buildings);
        building.setItemLabelGenerator(Building::getFullName);

        add(
                name,
                online,
                building,
                createButtonsLayout(),
                createSecondButtonLayout()
        );
    }

    private Component createSecondButtonLayout() {
        saveWithoutBuilding.setText(getTranslation("gui.button.saveWithoutBuilding"));
        saveWithoutBuilding.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        saveWithoutBuilding.addClickListener(event -> validateAndSaveWithoutBuilding());

        binder.addStatusChangeListener(event -> saveWithoutBuilding.setEnabled(binder.isValid()));

        return new HorizontalLayout(saveWithoutBuilding);
    }

    @Override
    protected SaveFormEvent<EditForm<Place>, Place> createSaveEvent() {
        return new PlaceSaveFormEvent(this, value);
    }

    @Override
    protected DeleteFormEvent<EditForm<Place>, Place> createDeleteEvent() {
        return new PlaceDeleteFormEvent(this, value);
    }

    @Override
    protected CloseFormEvent<EditForm<Place>, Place> createCloseEvent() {
        return new PlaceCloseFormEvent(this);
    }

    private void validateAndSaveWithoutBuilding() {
        try{
            binder.writeBean(value);
            fireEvent(new PlaceSaveWithoutBuildingFormEvent(this, value));
        } catch (ValidationException ex){
            ex.printStackTrace();
        }
    }

    public static class PlaceSaveFormEvent extends SaveFormEvent<EditForm<Place>, Place> {
        public PlaceSaveFormEvent(EditForm<Place> source, Place value) {
            super(source, value);
        }
    }

    public static class PlaceSaveWithoutBuildingFormEvent extends SaveFormEvent<EditForm<Place>, Place>{
        public PlaceSaveWithoutBuildingFormEvent(EditForm<Place> source, Place value) {
            super(source, value);
        }
    }

    public static class PlaceDeleteFormEvent extends DeleteFormEvent<EditForm<Place>, Place> {
        public PlaceDeleteFormEvent(EditForm<Place> source, Place value) {
            super(source, value);
        }
    }

    public static class PlaceCloseFormEvent extends CloseFormEvent<EditForm<Place>, Place> {
        public PlaceCloseFormEvent(EditForm<Place> source) {
            super(source);
        }
    }
}
