package kpn.financecontroller.gui.views.geo.place;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.place.Place;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.place.PlaceEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.views.EditForm;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "place", layout = MainLayout.class)
@PermitAll
final public class PlaceView extends GridView<Place> {

    @Autowired
    private DTOService<Place, PlaceEntity, Long> placeService;
    @Autowired
    private DTOService<Address, AddressEntity, Long> addressService;

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
        grid = new Grid<>(Place.class);
        grid.addClassName("address-grid");
        grid.setSizeFull();

        grid.setColumns();
        grid.addColumn(Place::getId).setHeader(getTranslation("gui.header.id"));
        grid.addColumn(Place::getName).setHeader(getTranslation("gui.header.name"));
        grid.addColumn(Place::isOnline).setHeader(getTranslation("gui.header.online"));
        grid.addColumn(p -> {return p.getAddress() != null ? p.getAddress().getInfo() : "-";}).setHeader(getTranslation("gui.header.address"));

        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new PlaceForm(addressService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(PlaceForm.PlaceSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(PlaceForm.PlaceSaveWithoutAddressFormEvent.class, this::handleSavingWithoutAddress);
        form.addListener(PlaceForm.PlaceDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(PlaceForm.PlaceCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Place());
    }

    @Override
    protected Result<Void> delete(Place domain) {
        return placeService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<Place> save(Place domain) {
        domain.setOnline(false);
        return placeService.saver().save(new PlaceEntity(domain));
    }

    private void handleSavingWithoutAddress(SaveFormEvent<EditForm<Place>, Place> event) {
        Place domain = event.getValue();
        domain.setAddress(null);
        domain.setOnline(true);
        handleSavingEvent(event);
    }
}
