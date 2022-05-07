package kpn.financecontroller.gui.views.geo.street;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
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
@Route(value = "street", layout = MainLayout.class)
@PermitAll
final public class StreetView extends GridView<Street>{

    @Autowired
    private DTOService<Street, StreetEntity, Long> streetService;
    @Autowired
    private DTOService<City, CityEntity, Long> cityService;

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Street>> result = streetService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(Street.class);
        grid.addClassName("country-grid");
        grid.setSizeFull();

        grid.setColumns();
        grid.addColumn(Street::getId).setHeader(getTranslation("gui.header.id"));
        grid.addColumn(Street::getName).setHeader(getTranslation("gui.header.name"));
        grid.addColumn(street -> street.getCity().getName()).setHeader(getTranslation("gui.header.city"));
        grid.addColumn(street -> street.getCity().getRegion().getName()).setHeader(getTranslation("gui.header.region"));
        grid.addColumn(street -> street.getCity().getRegion().getCountry().getName()).setHeader(getTranslation("gui.header.country"));
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new StreetForm(cityService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(StreetForm.StreetSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(StreetForm.StreetDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(StreetForm.StreetCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Street());
    }

    @Override
    protected Result<Void> delete(DeleteFormEvent<EditForm<Street>, Street> event) {
        return streetService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Street> save(SaveFormEvent<EditForm<Street>, Street> event) {
        return streetService.saver().save(new StreetEntity(event.getValue()));
    }
}