package kpn.financecontroller.gui.views.geo.city;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
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
    private Service<Long, City, Predicate, Result<List<City>>> cityService;
    @Autowired
    private Service<Long, Region, Predicate, Result<List<Region>>> regionService;

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
        form.addListener(CityForm.CityCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<List<City>> delete(City domain) {
        return cityService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<List<City>> save(City domain) {
        return cityService.saver().save(domain);
    }
}
