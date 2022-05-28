package kpn.financecontroller.gui.views.geo.country;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.lib.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@Scope("prototype")
@Route(value = "country", layout = MainLayout.class)
@PermitAll
final public class CountryView extends GridView<Country>{

    private static final List<ColumnConfig> COLUMN_CONFIGS = List.of(
            new ColumnConfig("gui.header.id", List.of("id")),
            new ColumnConfig("gui.header.name", List.of("name"))
    );

    @Autowired
    private DTOService<Country, CountryEntity> countryService;

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
        form = new CountryForm();
        form.setWidth("25em");

        form.addListener(CountryForm.CountrySaveFormEvent.class, this::handleSavingEvent);
        form.addListener(CountryForm.CountryDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(CountryForm.CountryCloseFormEvent.class, e -> closeEditor(true));
    }

    @Override
    protected Result<Void> delete(Country domain) {
        return countryService.deleter().byId(domain.getId());
    }

    @Override
    protected Result<Country> save(Country domain) {
        return countryService.saver().save(new CountryEntity(domain));
    }
}
