package kpn.financecontroller.gui.views.geo.city;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
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
@Route(value = "city", layout = MainLayout.class)
@PermitAll
final public class CityView extends GridView<City> {

    @Autowired
    private DTOService<City, CityEntity, Long> cityService;
    @Autowired
    private DTOService<Region, RegionEntity, Long> regionService;

    @Override
    protected Result<?> updateListImpl() {
        Result<List<City>> result = cityService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(City.class);
        grid.addClassName("country-grid");
        grid.setSizeFull();

        grid.setColumns();
        grid.addColumn(City::getId).setHeader(getTranslation("gui.header.id"));
        grid.addColumn(City::getName).setHeader(getTranslation("gui.header.name"));
        grid.addColumn(city -> city.getRegion().getName()).setHeader(getTranslation("gui.header.region"));
        grid.addColumn(city -> city.getRegion().getCountry().getName()).setHeader(getTranslation("gui.header.country"));

        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new CityForm(regionService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(CityForm.CitySaveFormEvent.class, this::saveContact);
        form.addListener(CityForm.CityDeleteFormEvent.class, this::deleteEvent);
        form.addListener(CityForm.CityCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new City());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<City>, City> event) {
        return cityService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<City> handleSaveEvent(SaveFormEvent<EditForm<City>, City> event) {
        return cityService.saver().save(new CityEntity(event.getValue()));
    }
}