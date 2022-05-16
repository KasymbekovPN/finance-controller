package kpn.financecontroller.gui.views.geo.city;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.services.dto.DTOService;
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
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.region", List.of("region", "name")),
            new ColumnConfig("gui.header.country", List.of("region", "country", "name"))
    );

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
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new CityForm(regionService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(CityForm.CitySaveFormEvent.class, this::handleSavingEvent);
        form.addListener(CityForm.CityDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(CityForm.CityCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new City());
    }

    @Override
    protected Result<Void> delete(City domain) {
        return cityService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<City> save(City domain) {
        return cityService.saver().save(new CityEntity(domain));
    }
}
