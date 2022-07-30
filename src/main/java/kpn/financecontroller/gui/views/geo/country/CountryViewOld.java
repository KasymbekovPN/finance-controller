package kpn.financecontroller.gui.views.geo.country;

import com.querydsl.core.types.Predicate;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.gui.views.GridViewOld;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "country", layout = MainLayout.class)
@PermitAll
final public class CountryViewOld extends GridViewOld<Country> {

    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name"))
    );

    @Autowired
    private Service<Long, Country, Predicate, Result<List<Country>>> countryService;

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Country>> result = countryService.loader().all();
        if (result.isSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid = new Grid<>(Country.class);
        grid.addClassName("country-grid");
        grid.setSizeFull();
        configureGridColumns(COLUMN_CONFIGS);
        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new CountryFormOld();
        form.setWidth("25em");

        form.addListener(CountryFormOld.CountrySaveFormEvent.class, this::handleSavingEvent);
        form.addListener(CountryFormOld.CountryDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(CountryFormOld.CountryCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<List<Country>> delete(Country domain) {
        return countryService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<List<Country>> save(Country domain) {
        return countryService.saver().save(domain);
    }
}
