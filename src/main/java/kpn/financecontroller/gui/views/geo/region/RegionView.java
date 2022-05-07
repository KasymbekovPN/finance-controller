package kpn.financecontroller.gui.views.geo.region;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.country.CountryEntity;
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
@Route(value = "region", layout = MainLayout.class)
@PermitAll
final public class RegionView extends GridView<Region>{

    @Autowired
    private DTOService<Region, RegionEntity, Long> regionService;
    @Autowired
    private DTOService<Country, CountryEntity, Long> countryService;

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

        grid.setColumns();
        grid.addColumn(Region::getId).setHeader(getTranslation("gui.header.id"));
        grid.addColumn(Region::getName).setHeader(getTranslation("gui.header.name"));
        grid.addColumn(region -> region.getCountry().getName()).setHeader(getTranslation("gui.header.country"));
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new RegionForm(countryService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(RegionForm.RegionSaveFormEvent.class, this::handleSavingEvent);
        form.addListener(RegionForm.RegionDeleteFormEvent.class, this::handleDeletingEvent);
        form.addListener(RegionForm.RegionCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Region());
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