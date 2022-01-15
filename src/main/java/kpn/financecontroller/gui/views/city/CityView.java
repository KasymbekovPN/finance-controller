package kpn.financecontroller.gui.views.city;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.gui.notifications.NotificationFactory;
import kpn.financecontroller.gui.notifications.Notifications;
import kpn.financecontroller.gui.views.MainLayout;
import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.message.LocaledMessageSeed;
import kpn.financecontroller.message.LocaledMessageSeedFactory;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;

@PageTitle("City")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "city", layout = MainLayout.class)
@PermitAll
public class CityView extends VerticalLayout {

    private final Grid<City> grid = new Grid<>(City.class);

    private final DTOService<City, CityEntity, Long> cityService;
    private final DTOService<Region, RegionEntity, Long> regionService;
    private final LocaledMessageSeedFactory seedFactory;
    private final I18nService i18nService;
    private final NotificationFactory notificationFactory;

    private CityForm form;

    @Autowired
    public CityView(DTOService<City, CityEntity, Long> cityService,
                    DTOService<Region, RegionEntity, Long> regionService,
                    LocaledMessageSeedFactory seedFactory,
                    I18nService i18nService,
                    NotificationFactory notificationFactory) {
        this.cityService = cityService;
        this.regionService = regionService;
        this.seedFactory = seedFactory;
        this.i18nService = i18nService;
        this.notificationFactory = notificationFactory;

        addClassName("city-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolBar(), getContent());

        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("country-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name");

        grid.addColumn(city -> city.getRegion().getName()).setHeader("Region");
        grid.addColumn(city -> city.getRegion().getCountry().getName()).setHeader("Country");

        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editCity(e.getValue()));
    }

    private void configureForm() {
        form = new CityForm(regionService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(CityForm.CitySaveFormEvent.class, this::saveContact);
        form.addListener(CityForm.CityDeleteFormEvent.class, this::deleteEvent);
        form.addListener(CityForm.CityCloseFormEvent.class, e -> closeEditor());
    }

    private Component getToolBar() {

        Button addContactButton = new Button("Add");
        addContactButton.addClickListener(e -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(addContactButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.addClassName("content");
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setSizeFull();
        return content;
    }

    private void updateList() {
        grid.setItems(cityService.loader().all().getValue());
    }

    private void closeEditor() {
        form.setCity(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void editCity(City city){
        if (city == null){
            closeEditor();
        } else {
            form.setCity(city);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteEvent(CityForm.CityDeleteFormEvent event) {
        cityService.deleter().byId(event.getValue().getId());

        updateList();
        closeEditor();
    }

    private void saveContact(CityForm.CitySaveFormEvent event) {
        Result<City> savingResult = cityService.saver().save(new CityEntity(event.getValue()));
        if (!savingResult.getSuccess()){
            LocaledMessageSeed seed = seedFactory.create(savingResult);
            String text = i18nService.getTranslation(seed);
            notificationFactory.getBuilder(Notifications.ERROR)
                    .duration(60_000)
                    .text(text)
                    .buttonIcon(new Icon("lumo", "cross"))
                    .buttonThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE)
                    .buttonAttribute("aria-label", "Close")
                    .build()
                    .open();
        }
        updateList();
        closeEditor();
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editCity(new City());
    }
}
