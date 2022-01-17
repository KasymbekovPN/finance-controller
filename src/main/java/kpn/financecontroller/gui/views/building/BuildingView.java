package kpn.financecontroller.gui.views.building;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.building.BuildingEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.views.EditForm;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.message.LocaledMessageSeedFactory;
import kpn.financecontroller.result.Result;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@PageTitle("Building")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "building", layout = MainLayout.class)
@PermitAll
public class BuildingView extends GridView<Building> {

    private final DTOService<Building, BuildingEntity, Long> buildingService;
    private final DTOService<Street, StreetEntity, Long> streetService;

    public BuildingView(LocaledMessageSeedFactory seedFactory,
                        I18nService i18nService,
                        NotificationFactory notificationFactory,
                        DTOService<Building, BuildingEntity, Long> buildingService,
                        DTOService<Street, StreetEntity, Long> streetService) {
        super(new Grid<>(Building.class), seedFactory, i18nService, notificationFactory);
        this.buildingService = buildingService;
        this.streetService = streetService;
    }

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Building>> result = buildingService.loader().all();
        if (result.getSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid.addClassName("building-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name");
        grid.addColumn(b -> b.getStreet().getName()).setHeader("Street");
        grid.addColumn(b -> b.getStreet().getCity().getName()).setHeader("City");
        grid.addColumn(b -> b.getStreet().getCity().getRegion().getName()).setHeader("Region");
        grid.addColumn(b -> b.getStreet().getCity().getRegion().getCountry().getName()).setHeader("Country");

        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new BuildingForm(streetService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(BuildingForm.BuildingSaveFormEvent.class, this::saveContact);
        form.addListener(BuildingForm.BuildingDeleteFormEvent.class, this::deleteEvent);
        form.addListener(BuildingForm.BuildingCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Building());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<Building>, Building> event) {
        return buildingService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Building> handleSaveEvent(SaveFormEvent<EditForm<Building>, Building> event) {
        return buildingService.saver().save(new BuildingEntity(event.getValue()));
    }
}
