package kpn.financecontroller.gui.views.geo.region;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.gui.views.GridViewOld;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "region", layout = MainLayout.class)
@PermitAll
final public class RegionViewOld extends GridViewOld<Region> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.country", List.of("country", "name"))
    );

    @Autowired
    private Service<Long, Region, Predicate, Result<List<Region>>> regionService;
    @Autowired
    private Service<Long, Country, Predicate, Result<List<Country>>> countryService;

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Region>> result = regionService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(Region.class);
        grid.addClassName("country-grid");
        grid.setSizeFull();
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new RegionFormOld(countryService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(RegionFormOld.RegionSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(RegionFormOld.RegionDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(RegionFormOld.RegionCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<List<Region>> delete(Region domain) {
        return regionService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<List<Region>> save(Region domain) {
        return regionService.saver().save(domain);
    }
}
