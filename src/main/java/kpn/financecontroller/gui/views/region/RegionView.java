package kpn.financecontroller.gui.views.region;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.country.CountryEntity;
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

@PageTitle("Region")
@org.springframework.stereotype.Component
@Scope("prototype")
@Route(value = "region", layout = MainLayout.class)
@PermitAll
public class RegionView extends VerticalLayout {

    private final Grid<Region> grid = new Grid<>(Region.class);

    private final DTOService<Region, RegionEntity, Long> regionService;
    private final DTOService<Country, CountryEntity, Long> countryService;
    private final LocaledMessageSeedFactory seedFactory;
    private final I18nService i18nService;
    private final NotificationFactory notificationFactory;

    private RegionForm form;

    @Autowired
    public RegionView(DTOService<Region, RegionEntity, Long> regionService,
                      DTOService<Country, CountryEntity, Long> countryService,
                      LocaledMessageSeedFactory seedFactory,
                      I18nService i18nService,
                      NotificationFactory notificationFactory) {
        this.regionService = regionService;
        this.countryService = countryService;
        this.seedFactory = seedFactory;
        this.i18nService = i18nService;
        this.notificationFactory = notificationFactory;

        addClassName("region-view");
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
        grid.addColumn(region -> region.getCountry().getName()).setHeader("Country");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editRegion(e.getValue()));
    }

    private void configureForm() {
        form = new RegionForm(countryService.loader().all().getValue());
        form.setWidth("25em");

        form.addListener(RegionForm.RegionSaveFormEvent.class, this::saveContact);
        form.addListener(RegionForm.RegionDeleteFormEvent.class, this::deleteEvent);
        form.addListener(RegionForm.RegionCloseFormEvent.class, e -> closeEditor());
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
        grid.setItems(regionService.loader().all().getValue());
    }

    private void closeEditor() {
        form.setRegion(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void editRegion(Region region){
        if (region == null){
            closeEditor();
        } else {
            form.setRegion(region);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteEvent(RegionForm.RegionDeleteFormEvent event) {
        regionService.deleter().byId(event.getValue().getId());

        updateList();
        closeEditor();
    }

    private void saveContact(RegionForm.RegionSaveFormEvent event) {
        Result<Region> savingResult = regionService.saver().save(new RegionEntity(event.getValue()));
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
        editRegion(new Region());
    }
}
