package kpn.financecontroller.gui.views.place;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.place.Place;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.place.PlaceEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.views.EditForm;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "place", layout = MainLayout.class)
@PermitAll
public class PlaceView extends GridView<Place> {

    private final DTOService<Place, PlaceEntity, Long> placeService;
    private final DTOService<Address, AddressEntity, Long> addressService;

    public PlaceView(NotificationFactory notificationFactory,
                     DTOService<Place, PlaceEntity, Long> placeService,
                     DTOService<Address, AddressEntity, Long> addressService) {
        super(new Grid<>(Place.class), notificationFactory, "gui.places");
        this.placeService = placeService;
        this.addressService = addressService;
    }

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Place>> result = placeService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid.addClassName("address-grid");
        grid.setSizeFull();

        grid.setColumns();
        grid.addColumn(Place::getId).setHeader(getTranslation("gui.id"));
        grid.addColumn(Place::getName).setHeader(getTranslation("gui.name"));
        grid.addColumn(Place::isOnline).setHeader(getTranslation("gui.online"));
        grid.addColumn(p -> {return p.getAddress() != null ? p.getAddress().getInfo() : "-";}).setHeader(getTranslation("gui.address"));

        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new PlaceForm(addressService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(PlaceForm.PlaceSaveFormEvent.class, this::saveContact);
        form.addListener(PlaceForm.PlaceSaveWithoutAddressFormEvent.class, this::saveContactWithoutAddress);
        form.addListener(PlaceForm.PlaceDeleteFormEvent.class, this::deleteEvent);
        form.addListener(PlaceForm.PlaceCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Place());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<Place>, Place> event) {
        return placeService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Place> handleSaveEvent(SaveFormEvent<EditForm<Place>, Place> event) {
        Place place = event.getValue();
        place.setOnline(false);
        return placeService.saver().save(new PlaceEntity(place));
    }

    private void saveContactWithoutAddress(SaveFormEvent<EditForm<Place>, Place> event) {
        Place place = event.getValue();
        place.setAddress(null);
        place.setOnline(true);
        Result<Place> savingResult = placeService.saver().save(new PlaceEntity(place));
        createNotification(savingResult);
        updateList();
        closeEditor();
    }
}
