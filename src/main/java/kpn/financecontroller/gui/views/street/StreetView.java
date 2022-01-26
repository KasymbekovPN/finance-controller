package kpn.financecontroller.gui.views.street;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
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
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "street", layout = MainLayout.class)
@PermitAll
public class StreetView extends GridView<Street>{

    private final DTOService<Street, StreetEntity, Long> streetService;
    private final DTOService<City, CityEntity, Long> cityService;

    public StreetView(LocaledMessageSeedFactory seedFactory,
                      I18nService i18nService,
                      NotificationFactory notificationFactory,
                      DTOService<Street, StreetEntity, Long> streetService,
                      DTOService<City, CityEntity, Long> cityService) {
        super(new Grid<>(Street.class), seedFactory, i18nService, notificationFactory, "gui.streets");
        this.streetService = streetService;
        this.cityService = cityService;
    }

    @Override
    protected Result<?> updateListImpl() {
        Result<List<Street>> result = streetService.loader().all();
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
        grid.addColumn(Street::getId).setHeader(getTranslation("gui.id"));
        grid.addColumn(Street::getName).setHeader(getTranslation("gui.name"));
        grid.addColumn(street -> street.getCity().getName()).setHeader(getTranslation("gui.city"));
        grid.addColumn(street -> street.getCity().getRegion().getName()).setHeader(getTranslation("gui.region"));
        grid.addColumn(street -> street.getCity().getRegion().getCountry().getName()).setHeader(getTranslation("gui.country"));
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editValue(e.getValue()));
    }

    @Override
    protected void configureForm() {
        form = new StreetForm(cityService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(StreetForm.StreetSaveFormEvent.class, this::saveContact);
        form.addListener(StreetForm.StreetDeleteFormEvent.class, this::deleteEvent);
        form.addListener(StreetForm.StreetCloseFormEvent.class, e -> closeEditor());
    }

    @Override
    protected void add() {
        grid.asSingleSelect().clear();
        editValue(new Street());
    }

    @Override
    protected Result<Void> handleDeleteEvent(DeleteFormEvent<EditForm<Street>, Street> event) {
        return streetService.deleter().byId(event.getValue().getId());
    }

    @Override
    protected Result<Street> handleSaveEvent(SaveFormEvent<EditForm<Street>, Street> event) {
        return streetService.saver().save(new StreetEntity(event.getValue()));
    }
}