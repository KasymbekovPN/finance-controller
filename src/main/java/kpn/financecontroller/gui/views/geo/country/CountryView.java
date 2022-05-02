package kpn.financecontroller.gui.views.geo.country;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
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
@Route(value = "country", layout = MainLayout.class)
@PermitAll
final public class CountryView extends GridView<Country>{

    @Autowired
    private DTOService<Country, CountryEntity, Long> countryService;

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

        grid.setColumns();
        grid.addColumn(Country::getId).setHeader(getTranslation("gui.header.id"));
        grid.addColumn(Country::getInfo).setHeader(getTranslation("gui.header.name"));
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new CountryForm();
        form.setWidth("25em");

        form.addListener(CountryForm.CountrySaveFormEvent.class, this::saveContact);
        form.addListener(CountryForm.CountryDeleteFormEvent.class, this::deleteEvent);
        form.addListener(CountryForm.CountryCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Country());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<Country>, Country> event) {
        return countryService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Country> handleSaveEvent(SaveFormEvent<EditForm<Country>, Country> event) {
        return countryService.saver().save(new CountryEntity(event.getValue()));
    }
}