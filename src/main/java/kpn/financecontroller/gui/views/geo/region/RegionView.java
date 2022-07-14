package kpn.financecontroller.gui.views.geo.region;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.services.dto.DTOServiceOLdOld;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "region", layout = MainLayout.class)
@PermitAll
final public class RegionView extends GridView<Region>{
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.country", List.of("country", "name"))
    );

    @Autowired
    private DTOServiceOLdOld<Region, RegionEntity> regionService;
    @Autowired
    private DTOServiceOLdOld<Country, CountryEntity> countryService;

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
        form = new RegionForm(countryService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(RegionForm.RegionSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(RegionForm.RegionDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(RegionForm.RegionCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<Void> delete(Region domain) {
        return regionService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<Region> save(Region domain) {
        return regionService.saver().save(new RegionEntity(domain));
    }
}
