package kpn.financecontroller.gui.views.geo.street;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.gui.views.GridViewOld;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "street", layout = MainLayout.class)
@PermitAll
final public class StreetViewOld extends GridViewOld<Street> {
    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name")),
            new ColumnConfig("gui.header.city", List.of("city", "name")),
            new ColumnConfig("gui.header.region", List.of("city", "region", "name")),
            new ColumnConfig("gui.header.country", List.of("city", "region", "country", "name"))
    );

    @Autowired
    private Service<Long, Street, Predicate, Result<List<Street>>> streetService;
    @Autowired
    private Service<Long, City, Predicate, Result<List<City>>> cityService;

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
        form = new StreetFormOld(cityService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(StreetFormOld.StreetSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(StreetFormOld.StreetDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(StreetFormOld.StreetCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<List<Street>> delete(Street domain) {
        return streetService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<List<Street>> save(Street domain) {
        return streetService.saver().save(domain);
    }
}
