package kpn.financecontroller.gui.views.geo.street;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.dto.DTOService;
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
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.city", List.of("city", "name")),
            new ColumnConfig("gui.header.region", List.of("city", "region", "name")),
            new ColumnConfig("gui.header.country", List.of("city", "region", "country", "name"))
    );

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
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new StreetForm(cityService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(StreetForm.StreetSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(StreetForm.StreetDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(StreetForm.StreetCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<Void> delete(Street domain) {
        return streetService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<Street> save(Street domain) {
        return streetService.saver().save(new StreetEntity(domain));
    }
}
