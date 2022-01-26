package kpn.financecontroller.gui.views.region;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.events.DeleteFormEvent;
import kpn.financecontroller.gui.events.SaveFormEvent;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.views.EditForm;
import kpn.financecontroller.gui.views.GridView;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.message.LocaledMessageSeedFactory;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "region", layout = MainLayout.class)
@PermitAll
public class RegionView extends GridView<Region>{

    private final DTOService<Region, RegionEntity, Long> regionService;
    private final DTOService<Country, CountryEntity, Long> countryService;

    @Autowired
    public RegionView(LocaledMessageSeedFactory seedFactory,
                      I18nService i18nService,
                      NotificationFactory notificationFactory,
                      DTOService<Region, RegionEntity, Long> regionService,
                      DTOService<Country, CountryEntity, Long> countryService) {
        super(new Grid<>(Region.class), seedFactory, i18nService, notificationFactory, "gui.regions");
        this.regionService = regionService;
        this.countryService = countryService;
    }

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Region>> result = regionService.loader().all();
        if (result.getSuccess()){
            grid.setItems(result.getValue());
        }
        return result;
    }

    @Override
    protected void configureGrid() {
        grid.addClassName("country-grid");
        grid.setSizeFull();

        grid.setColumns();
        grid.addColumn(Region::getId).setHeader(getTranslation("gui.id"));
        grid.addColumn(Region::getName).setHeader(getTranslation("gui.name"));
        grid.addColumn(region -> region.getCountry().getName()).setHeader(getTranslation("gui.country"));
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new RegionForm(countryService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(RegionForm.RegionSaveFormEvent.class, this::saveContact);
        form.addListener(RegionForm.RegionDeleteFormEvent.class, this::deleteEvent);
        form.addListener(RegionForm.RegionCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Region());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<Region>, Region> event) {
        return regionService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Region> handleSaveEvent(SaveFormEvent<EditForm<Region>, Region> event) {
        return regionService.saver().save(new RegionEntity(event.getValue()));
    }
}